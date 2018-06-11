package vswe.stevescarts.containers.slots;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotBuilder extends SlotBase {
	public SlotBuilder(final IInventory iinventory, final int i, final int j, final int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return Block.getBlockFromItem(itemstack.getItem()) instanceof BlockRailBase;
	}
}
