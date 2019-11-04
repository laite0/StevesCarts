package vswe.stevescarts.impl.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.listeners.CartTick;
import vswe.stevescarts.api.listeners.PlayerInteract;
import vswe.stevescarts.api.network.Synced;
import vswe.stevescarts.impl.network.SyncedHandler;
import vswe.stevescarts.impl.util.InventoryBase;

public class CartEntity extends AbstractMinecartEntity {

	private ComponentStore componentStore = new ComponentStore(this);
	public InventoryBase inventory = new InventoryBase(12);
	public boolean cornerFlip; //TODO
	private boolean oldRender;
	private int wrongRender;
	private float lastRenderYaw;
	private double lastMotionX;
	private double lastMotionZ;

	@Synced
	public int workingTime = 0;

	public CartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public CartEntity(World world, double x, double y, double z) {
		super(StevesCarts.cartEntityType, world, x, y, z);
	}

	@Override
	public void tick() {
		super.tick();
		componentStore.fire(CartTick.class);

		if(!world.isClient){
			ServerWorld serverWorld = (ServerWorld) world;
			serverWorld.method_14178().sendToNearbyPlayers(this, createSyncPacket());
			if(workingTime > 0) {
				workingTime--;
			}
		}

	}

	@Override
	public boolean interact(PlayerEntity playerEntity, Hand hand) {
		if(hand == Hand.MAIN_HAND) {
			componentStore.fire(PlayerInteract.class, playerEntity);
			if(!world.isClient) {
				ContainerProviderRegistry.INSTANCE.openContainer(new Identifier(StevesCarts.MOD_ID, "cart"), playerEntity, byteBuf -> byteBuf.writeInt(getEntityId()));
			}
		}

		return super.interact(playerEntity, hand);
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag compoundTag) {
		super.readCustomDataFromTag(compoundTag);
		inventory.deserializeNBT(compoundTag.getCompound("inv"));
	}

	@Override
	protected void writeCustomDataToTag(CompoundTag compoundTag) {
		super.writeCustomDataToTag(compoundTag);
		compoundTag.put("inv", inventory.serializeNBT());
	}

	public boolean hasFuel() {
		return getComponentStore().stream().anyMatch(Component::isProvidingFuel);
	}

	public boolean canDoNewWork() {
		return workingTime <= 0 && hasFuel();
	}

	@Override
	protected void method_7513(BlockPos blockPos, BlockState blockState) {
		RailShape shapeProperty = blockState.get(((RailBlock) blockState.getBlock()).getShapeProperty());
		cornerFlip = ((shapeProperty == RailShape.SOUTH_EAST || shapeProperty == RailShape.SOUTH_WEST) && getVelocity().x < 0.0) || ((shapeProperty == RailShape.NORTH_EAST || shapeProperty == RailShape.NORTH_WEST) && getVelocity().x > 0.0);

		if(workingTime > 0) {
			return;
		}
		super.method_7513(blockPos, blockState);

		if(!hasFuel()) {
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

	public boolean getRenderFlippedYaw(float yaw) {
		yaw %= 360.0f;
		if (yaw < 0.0f) {
			yaw += 360.0f;
		}
		final double motionX = getVelocity().x;
		final double motionY = getVelocity().y;
		final double motionZ = getVelocity().z;

		if (!oldRender || Math.abs(yaw - lastRenderYaw) < 90.0f || Math.abs(yaw - lastRenderYaw) > 270.0f || (motionX > 0.0 && lastMotionX < 0.0) || (motionZ > 0.0 && lastMotionZ < 0.0)
				|| (motionX < 0.0 && lastMotionX > 0.0) || (motionZ < 0.0 && lastMotionZ > 0.0) || wrongRender >= 50) {
			lastMotionX = motionX;
			lastMotionZ = motionZ;
			lastRenderYaw = yaw;
			oldRender = true;
			wrongRender = 0;
			return false;
		}
		++wrongRender;
		return true;
	}

	//Nope
	@Override
	protected boolean canAddPassenger(Entity entity) {
		return false;
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
