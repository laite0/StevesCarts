package vswe.stevescarts.impl.network;

import io.netty.buffer.Unpooled;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.network.Synced;
import vswe.stevescarts.impl.entity.CartEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SyncedHandler {

	public static Packet<?> buildSyncPacket(CartEntity cartEntity) {
		return new CustomPayloadS2CPacket(new Identifier(StevesCarts.MOD_ID, "sync_cart"), buildSyncBuffer(cartEntity));
	}

	public static ExtendedPacketBuffer buildSyncBuffer(CartEntity cartEntity) {
		ExtendedPacketBuffer buf = new ExtendedPacketBuffer(new PacketByteBuf(Unpooled.buffer()));

		buf.writeInt(cartEntity.getEntityId());
		writeObjectToBuffer(cartEntity, buf);
		buf.writeInt(cartEntity.getComponentStore().size());

		cartEntity.getComponentStore().forEach((component, settings) -> {
			buf.writeIdentifier(settings.getId());
			writeObjectToBuffer(component, buf);
		});
		return buf;
	}

	private static void writeObjectToBuffer(Object object, ExtendedPacketBuffer byteBuf) {
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Synced.class)) {
				try {
					byteBuf.writeString(field.getName());
					field.setAccessible(true);
					if(Modifier.isStatic(field.getModifiers()) ) {
						throw new UnsupportedOperationException("Field cannot be static");
					}
					ObjectBufferUtils.writeObject(field.get(object), byteBuf);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static List<DataHolder> readSyncBuffer(CartEntity cartEntity, ExtendedPacketBuffer buf) {
		List<DataHolder> dataHolders = new ArrayList<>();
		readObjectFromBuffer(cartEntity, buf, dataHolders);
		final int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			Identifier identifier = buf.readIdentifier();
			Component component = cartEntity.getComponentStore().getById(identifier);
			if(component == null) {
				throw new UnsupportedOperationException("Could not find " + identifier + "on the cart!");
			}
			readObjectFromBuffer(component, buf, dataHolders);
		}
		return dataHolders;
	}

	private static void readObjectFromBuffer(Object targetObject, ExtendedPacketBuffer buf, List<DataHolder> dataHolders) {
		for (Field field : targetObject.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(Synced.class)) {
				String name = buf.readString();
				if (!field.getName().equals(name)) {
					throw new UnsupportedOperationException("Field name does not match, are you using the same version on the client and the server?");
				}
				Object object = ObjectBufferUtils.readObject(buf);
				field.setAccessible(true);
				dataHolders.add(new DataHolder(field, targetObject, object));
			}
		}
	}

	public static class DataHolder {
		final Field field;
		final Object targetObject;
		final Object object;

		public DataHolder(Field field, Object targetObject, Object object) {
			this.field = field;
			this.targetObject = targetObject;
			this.object = object;
		}

		public void apply() {
			try {
				field.set(targetObject, object);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
