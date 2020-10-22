package vswe.stevescarts.impl.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import vswe.stevescarts.impl.entity.CartEntity;

@Environment(EnvType.CLIENT)
public class CartEntityRenderer extends EntityRenderer<CartEntity> {
	private final EntityModel<CartEntity> model = new MinecartEntityModel<>();

	public CartEntityRenderer(EntityRenderDispatcher erd) {
		super(erd);
	}

	@Override
	public void render(CartEntity cartEntity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		super.render(cartEntity, yaw, tickDelta, matrices, vertexConsumers, light);
		matrices.push();
		final double partialPosX = MathHelper.lerp(tickDelta, cartEntity.lastRenderX, cartEntity.getX());
		final double partialPosY = MathHelper.lerp(tickDelta, cartEntity.lastRenderY, cartEntity.getY());
		final double partialPosZ = MathHelper.lerp(tickDelta, cartEntity.lastRenderZ, cartEntity.getZ());
		float partialRotPitch = MathHelper.lerp(tickDelta, cartEntity.prevPitch, cartEntity.pitch);

		final Vec3d posFromRail = cartEntity.snapPositionToRail(partialPosX, partialPosY, partialPosZ);
		if (posFromRail != null) {
			Vec3d lastPos = cartEntity.snapPositionToRailWithOffset(partialPosX, partialPosY, partialPosZ, 0.30000001192092896D);
			Vec3d nextPos = cartEntity.snapPositionToRailWithOffset(partialPosX, partialPosY, partialPosZ, -0.30000001192092896D);

			if (lastPos == null) {
				lastPos = posFromRail;
			}
			if (nextPos == null) {
				nextPos = posFromRail;
			}

			matrices.translate(posFromRail.x - partialPosX, (lastPos.y + nextPos.y) / 2.0D - partialPosY, posFromRail.z - partialPosZ);
			Vec3d difference = nextPos.add(-lastPos.x, -lastPos.y, -lastPos.z);
			if (difference.length() != 0.0) {
				difference = difference.normalize();
				yaw = (float) (Math.atan2(difference.z, difference.x) * 180.0 / 3.141592653589793);
				partialRotPitch = (float) (Math.atan(difference.y) * 73.0);
			}
		}

		matrices.translate(0.0D, 0.375D, 0.0D);
		matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - yaw));
		matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-partialRotPitch));
		float damageTime = cartEntity.getDamageWobbleTicks() - tickDelta;
		float damageRot = cartEntity.getDamageWobbleStrength() - tickDelta;
		final float damageDir = cartEntity.getDamageWobbleSide();

		if (damageRot < 0.0f) {
			damageRot = 0.0f;
		}
		if (damageTime > 0.0F) {
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(damageTime) * damageTime * damageRot / 10.0F * damageDir));
		}

		matrices.scale(-1.0F, -1.0F, 1.0F);
		this.model.setAngles(cartEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(cartEntity)));
		this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

//		cartEntity.getComponentStore().forEach((component, settings) -> {
//			bindTexture(settings.getRenderer().textureLocation(component));
//			settings.getRenderer().render(component, cartEntity, 0, 0, 0, tickDelta, this);
//		});

		matrices.pop();

	}

	@Override
	public Identifier getTexture(CartEntity cartEntity) {
		return new Identifier("textures/entity/minecart.png");
	}
}
