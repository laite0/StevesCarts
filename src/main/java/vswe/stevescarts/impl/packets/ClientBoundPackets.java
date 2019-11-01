package vswe.stevescarts.impl.packets;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.impl.entity.CartEntity;

import java.util.UUID;

public class ClientBoundPackets {

	public static void init() {
		ClientSidePacketRegistry.INSTANCE.register(new Identifier(StevesCarts.MOD_ID, "spawn_cart"), (PacketConsumer) (packetContext, packetByteBuf) -> {
			double x = packetByteBuf.readDouble();
			double y = packetByteBuf.readDouble();
			double z = packetByteBuf.readDouble();
			float pitch = packetByteBuf.readFloat();
			float yaw = packetByteBuf.readFloat();
			int id = packetByteBuf.readInt();
			UUID uuid = packetByteBuf.readUuid();

			packetContext.getTaskQueue().execute(() -> {
				CartEntity entity = new CartEntity(packetContext.getPlayer().world, x, y, z);

				entity.updateTrackedPosition(x, y, z);
				entity.pitch = (float) (pitch * 360) / 256.0F;
				entity.yaw = (float) (yaw * 360) / 256.0F;
				entity.setEntityId(id);
				entity.setUuid(uuid);
				((ClientWorld)packetContext.getPlayer().world).addEntity(id, entity);
			});
		});
	}

}
