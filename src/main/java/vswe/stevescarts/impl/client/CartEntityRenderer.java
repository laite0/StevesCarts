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
	public void render(CartEntity cartEntity, double x, double y, double z, float yaw, float deltaTicks) {
		GlStateManager.pushMatrix();
		this.bindEntityTexture(cartEntity);

		double renderX = MathHelper.lerp(deltaTicks, cartEntity.prevRenderX, cartEntity.x);
		double renderY = MathHelper.lerp(deltaTicks, cartEntity.prevRenderY, cartEntity.y);
		double renderZ = MathHelper.lerp(deltaTicks, cartEntity.prevRenderZ, cartEntity.z);

		Vec3d vec3d_1 = cartEntity.method_7508(renderX, renderY, renderZ);
		float pitch = MathHelper.lerp(deltaTicks, cartEntity.prevPitch, cartEntity.pitch);
		if (vec3d_1 != null) {
			Vec3d vec3d_2 = cartEntity.method_7505(renderX, renderY, renderZ, 0.30000001192092896D);
			Vec3d vec3d_3 = cartEntity.method_7505(renderX, renderY, renderZ, -0.30000001192092896D);
			if (vec3d_2 == null) {
				vec3d_2 = vec3d_1;
			}

			if (vec3d_3 == null) {
				vec3d_3 = vec3d_1;
			}

			x += vec3d_1.x - renderX;
			y += (vec3d_2.y + vec3d_3.y) / 2.0D - renderY;
			z += vec3d_1.z - renderZ;
			Vec3d vec3d_4 = vec3d_3.add(-vec3d_2.x, -vec3d_2.y, -vec3d_2.z);

			if (vec3d_4.length() != 0.0D) {
				vec3d_4 = vec3d_4.normalize();
				yaw = (float)(Math.atan2(vec3d_4.z, vec3d_4.x) * 180.0D / 3.141592653589793D);
				pitch = (float)(Math.atan(vec3d_4.y) * 73.0D);
			}
		}

		GlStateManager.translatef((float)x, (float)y + 0.375F, (float)z);
		GlStateManager.rotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-pitch, 0.0F, 0.0F, 1.0F);
		float wobbleTicks = (float)cartEntity.getDamageWobbleTicks() - deltaTicks;
		float wobbleStrength = cartEntity.getDamageWobbleStrength() - deltaTicks;
		if (wobbleStrength < 0.0F) {
			wobbleStrength = 0.0F;
		}

		if (wobbleTicks > 0.0F) {
			GlStateManager.rotatef(MathHelper.sin(wobbleTicks) * wobbleTicks * wobbleStrength / 10.0F * (float)cartEntity.getDamageWobbleSide(), 1.0F, 0.0F, 0.0F);
		}

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.setupSolidRenderingTextureCombine(this.getOutlineColor(cartEntity));
		}

		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		this.model.render(cartEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);


		cartEntity.getComponentStore().forEach((component, settings) -> {
			bindTexture(settings.getRenderer().textureLocation(component));
			settings.getRenderer().render(component, cartEntity, 0, 0, 0, 0.0625F, this);
		});

		GlStateManager.popMatrix();


		if (this.renderOutlines) {
			GlStateManager.tearDownSolidRenderingTextureCombine();
			GlStateManager.disableColorMaterial();
		}

		super.render(cartEntity, x, y, z, yaw, deltaTicks);
	}

	@Override
	protected Identifier getTexture(CartEntity var1) {
		return new Identifier("textures/entity/minecart.png");
	}
}
