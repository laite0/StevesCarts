package vswe.stevescarts.helpers.storages;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vswe.stevescarts.guis.GuiBase;

import javax.annotation.Nonnull;

public interface ITankHolder {
	@Nonnull
	ItemStack getInputContainer(final int p0);

	void setInputContainer(final int p0, ItemStack stack);

	void addToOutputContainer(final int p0, @Nonnull ItemStack p1);

	void onFluidUpdated(final int p0);

	@SideOnly(Side.CLIENT)
	void drawImage(int tankid, GuiBase gui, TextureAtlasSprite sprite, int targetX, int targetY, int srcX, int srcY, int width, int height);

}
