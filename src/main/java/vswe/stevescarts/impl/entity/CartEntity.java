package vswe.stevescarts.impl.entity;

import io.netty.buffer.Unpooled;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.listeners.CartTick;
import vswe.stevescarts.api.listeners.PlayerInteract;
import vswe.stevescarts.impl.network.SyncedHandler;

public class CartEntity extends AbstractMinecartEntity {

	private ComponentStore componentStore = new ComponentStore(this);

	public CartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public CartEntity(World world, double x, double y, double z) {
		super(StevesCarts.cartEntityType, world, x, y, z);
	}

	@Override
	public void tick() {
		super.tick();
		allowMovement = false;
		componentStore.fire(CartTick.class);

		if(!world.isClient){
			ServerWorld serverWorld = (ServerWorld) world;
			serverWorld.method_14178().sendToNearbyPlayers(this, createSyncPacket());
		}
	}

	@Override
	public boolean interact(PlayerEntity playerEntity, Hand hand) {
		if(hand == Hand.MAIN_HAND) {
			componentStore.fire(PlayerInteract.class, playerEntity);
		}

		return super.interact(playerEntity, hand);
	}

	//TODO make this better
	public boolean allowMovement = false;

	@Override
	protected void method_7513(BlockPos blockPos, BlockState blockState) {
		super.method_7513(blockPos, blockState);

		if(!allowMovement) {
			return;
		}

		double targetSpeed = 0.01;

		AbstractRailBlock currentRail = (AbstractRailBlock) blockState.getBlock();
		RailShape railShape = blockState.get(currentRail.getShapeProperty());

		Vec3d velocity = this.getVelocity();
		double speed = Math.sqrt(squaredHorizontalLength(velocity));
		if (speed > 0.01D) {
			if (targetSpeed == 0) {

				//TODO bring it to a stop?
			} else {
				this.setVelocity(velocity.add(velocity.x / speed * targetSpeed, 0.0D, velocity.z / speed * targetSpeed));
			}

		} else if (targetSpeed > 0) {
			velocity = this.getVelocity();
			double velocityX = velocity.x;
			double velocityY = velocity.z;
			if (railShape == RailShape.EAST_WEST) {
				velocityX = 0.02D;
			} else {
				if (railShape != RailShape.NORTH_SOUTH) {
					return;
				}
				velocityY = -0.02D;
			}

			this.setVelocity(velocityX, velocity.y, velocityY);
		}

	}

	@Override
	public Packet<?> createSpawnPacket() {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(pitch);
		buf.writeFloat(yaw);
		buf.writeInt(getEntityId());
		buf.writeUuid(getUuid());

		return new CustomPayloadS2CPacket(new Identifier(StevesCarts.MOD_ID, "spawn_cart"), buf);
	}

	public Packet<?> createSyncPacket() {
		return SyncedHandler.buildSyncPacket(this);
	}

	@Override
	public Type getMinecartType() {
		return Type.RIDEABLE;
	}

	public ComponentStore getComponentStore() {
		return componentStore;
	}
}
