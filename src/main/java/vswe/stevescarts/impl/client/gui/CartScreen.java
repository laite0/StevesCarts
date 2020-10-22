package vswe.stevescarts.impl.client.gui;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.impl.entity.CartEntity;

public class CartScreen extends HandledScreen<CartScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(StevesCarts.MOD_ID, "textures/gui/cart.png");

	public CartScreen(CartScreenHandler cartScreenHandler) {
		super(cartScreenHandler, cartScreenHandler.playerInventory, new LiteralText("Cart"));
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		client.getTextureManager().bindTexture(TEXTURE);
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

		//TODO: fixme
		//drawEntity(left + 40, top + 60, 30, container.cartEntity);
	}


	public static void drawEntity(float x, float y, float size, CartEntity cartEntity) {
//		GlStateManager.enableColorMaterial();
//		GlStateManager.pushMatrix();
//		GlStateManager.translatef(x, y, 50.0F);
//		GlStateManager.scalef(-size, size, size);
//		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
//		GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
//		GuiLighting.enable();
//
//		GlStateManager.rotatef(-100.0F, 0.0F, 1.0F, 0.0F);
//		GlStateManager.rotatef(30.0F, 1.0F, 0.0F, 0.0F);
//		GlStateManager.translatef(0.0F, 0.0F, 0.0F);
//
//		EntityRenderDispatcher erd = MinecraftClient.getInstance().getEntityRenderManager();
//		erd.method_3945(180.0F);
//		erd.setRenderShadows(false);
//		erd.render(cartEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
//		erd.setRenderShadows(true);
//
//		GlStateManager.popMatrix();
//		GuiLighting.disable();
//		GlStateManager.disableRescaleNormal();
//		GlStateManager.activeTexture(GLX.GL_TEXTURE1);
//		GlStateManager.disableTexture();
//		GlStateManager.activeTexture(GLX.GL_TEXTURE0);
	}


}
