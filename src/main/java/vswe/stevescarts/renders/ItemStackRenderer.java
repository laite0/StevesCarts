package vswe.stevescarts.renders;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import vswe.stevescarts.items.ModItems;
import vswe.stevescarts.models.ModelCartbase;
import vswe.stevescarts.modules.data.ModuleData;

import java.util.HashMap;

public class ItemStackRenderer extends TileEntityItemStackRenderer {

	private TileEntityItemStackRenderer renderer;

	public ItemStackRenderer(TileEntityItemStackRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void renderByItem(ItemStack itemStack) {
		if (itemStack.getItem() != ModItems.CARTS) {
			renderer.renderByItem(itemStack);
			return;
		}
		GlStateManager.pushMatrix();
		GlStateManager.scale(-1.0f, -1.0f, 1.0f);
		final NBTTagCompound info = itemStack.getTagCompound();
		if (info != null) {
			final NBTTagByteArray moduleIDTag = (NBTTagByteArray) info.getTag("Modules");
			final byte[] bytes = moduleIDTag.getByteArray();
			final HashMap<String, ModelCartbase> models = new HashMap<>();
			float lowestMult = 1.0f;
			for (final byte id : bytes) {
				final ModuleData module = ModuleData.getList().get(id);
				if (module != null && module.haveModels(true)) {
					if (module.getModelMult() < lowestMult) {
						lowestMult = module.getModelMult();
					}
					models.putAll(module.getModels(true));
				}
			}
			for (final byte id : bytes) {
				final ModuleData module = ModuleData.getList().get(id);
				if (module != null && module.haveRemovedModels()) {
					for (final String str : module.getRemovedModels()) {
						models.remove(str);
					}
				}
			}
			GlStateManager.rotate(90F, 0F, 1, 0);
			GlStateManager.translate(-1, -0.75, 0);
			GlStateManager.scale(lowestMult, lowestMult, lowestMult);
			for (final ModelCartbase model : models.values()) {
				model.render(null, null, 0.0f, 0.0f, 0.0f, 0.0625f, 0.0f);
			}
		}
		GlStateManager.popMatrix();
	}
}
