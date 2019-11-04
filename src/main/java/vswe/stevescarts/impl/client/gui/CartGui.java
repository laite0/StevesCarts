package vswe.stevescarts.impl.client.gui;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.impl.entity.CartEntity;

public class CartGui extends AbstractContainerScreen<CartContainer> {

	public CartGui(CartContainer cartContainer) {
		super(cartContainer, cartContainer.playerInventory, new LiteralText("Cart"));
	}

	@Override
	protected void drawBackground(float var1, int var2, int var3) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bindTexture(new Identifier(StevesCarts.MOD_ID, "textures/gui/cart.png"));
		this.blit(left, top, 0, 0, containerWidth, containerHeight);

		drawEntity(left + 40, top + 60, 30, container.cartEntity);
	}

	public static void drawEntity(float x, float y, float size, CartEntity cartEntity) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(x, y, 50.0F);
		GlStateManager.scalef(-size, size, size);
		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
		GuiLighting.enable();

		GlStateManager.rotatef(-100.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(30.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translatef(0.0F, 0.0F, 0.0F);

		EntityRenderDispatcher erd = MinecraftClient.getInstance().getEntityRenderManager();
		erd.method_3945(180.0F);
		erd.setRenderShadows(false);
		erd.render(cartEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		erd.setRenderShadows(true);
		
		GlStateManager.popMatrix();
		GuiLighting.disable();
		GlStateManager.disableRescaleNormal();
		GlStateManager.activeTexture(GLX.GL_TEXTURE1);
		GlStateManager.disableTexture();
		GlStateManager.activeTexture(GLX.GL_TEXTURE0);
	}
}
