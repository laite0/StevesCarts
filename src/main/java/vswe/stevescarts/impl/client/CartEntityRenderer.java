package vswe.stevescarts.impl.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import vswe.stevescarts.impl.entity.CartEntity;

public class CartEntityRenderer extends EntityRenderer<CartEntity> {
	private final EntityModel<CartEntity> model = new MinecartEntityModel<>();

	public CartEntityRenderer(EntityRenderDispatcher erd) {
		super(erd);
	}

	@Override
	public void render(CartEntity cartEntity, double x, double y, double z, float yaw, final float deltaTicks) {
		GlStateManager.pushMatrix();
		this.bindEntityTexture(cartEntity);

		final double partialPosX = MathHelper.lerp(deltaTicks, cartEntity.prevRenderX, cartEntity.x);
		final double partialPosY = MathHelper.lerp(deltaTicks, cartEntity.prevRenderY, cartEntity.y);
		final double partialPosZ = MathHelper.lerp(deltaTicks, cartEntity.prevRenderZ, cartEntity.z);
		float partialRotPitch = MathHelper.lerp(deltaTicks, cartEntity.prevPitch, cartEntity.pitch);

		final Vec3d posFromRail = cartEntity.getPos();
		if (posFromRail != null) {
			Vec3d lastPos = cartEntity.method_7505(partialPosX, partialPosY, partialPosZ, 0.30000001192092896D);
			Vec3d nextPos = cartEntity.method_7505(partialPosX, partialPosY, partialPosZ, -0.30000001192092896D);

			if (lastPos == null) {
				lastPos = posFromRail;
			}
			if (nextPos == null) {
				nextPos = posFromRail;
			}

//			x += posFromRail.x - partialPosX;
//			y += (lastPos.y + nextPos.y) / 2.0 - partialPosY;
//			z += posFromRail.z - partialPosZ;

			Vec3d difference = nextPos.add(-lastPos.x, -lastPos.y, -lastPos.z);
			if (difference.length() != 0.0) {
				difference = difference.normalize();
				yaw = (float) (Math.atan2(difference.z, difference.x) * 180.0 / 3.141592653589793);
				partialRotPitch = (float) (Math.atan(difference.y) * 73.0);
			}
		}

		yaw = 180.0f - yaw;
		partialRotPitch *= -1.0f;
		float damageRot = cartEntity.getDamageWobbleTicks() - deltaTicks;
		float damageTime = cartEntity.getDamageWobbleStrength() - deltaTicks;
		final float damageDir = cartEntity.getDamageWobbleSide();

		if (damageTime < 0.0f) {
			damageTime = 0.0f;
		}

		boolean flip = cartEntity.getVelocity().x > 0.0 != cartEntity.getVelocity().z > 0.0;
		if (cartEntity.cornerFlip) {
			flip = !flip;
		}

		if (cartEntity.getRenderFlippedYaw(yaw + (flip ? 0.0f : 180.0f))) {
			flip = !flip;
		}

		GlStateManager.translatef((float) x, (float) y + 0.375F, (float) z);
		GlStateManager.rotatef(yaw, 0.0f, 1.0f, 0.0f);
		GlStateManager.rotatef(partialRotPitch, 0.0f, 0.0f, 1.0f);

		if (damageRot > 0.0f) {
			damageRot = MathHelper.sin(damageRot) * damageRot * damageTime / 10.0f * damageDir;
			GlStateManager.rotatef(damageRot, 1.0f, 0.0f, 0.0f);
		}

		yaw += (flip ? 0.0f : 180.0f);
		GlStateManager.rotatef(flip ? 0.0f : 180.0f, 0.0f, 1.0f, 0.0f);
		GlStateManager.scalef(-1.0f, -1.0f, 1.0f);
		this.model.render(cartEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);


		cartEntity.getComponentStore().forEach((component, settings) -> {
			bindTexture(settings.getRenderer().textureLocation(component));
			settings.getRenderer().render(component, cartEntity, 0, 0, 0, deltaTicks, this);
		});

		GlStateManager.popMatrix();
		super.render(cartEntity, x, y, z, yaw, deltaTicks);
	}

	@Override
	protected Identifier getTexture(CartEntity var1) {
		return new Identifier("textures/entity/minecart.png");
	}
}
