package stevesvehicles.common.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import stevesvehicles.common.items.ComponentTypes;
import stevesvehicles.common.items.ModItems;

public class SlotCakeDynamite extends SlotCake implements ISlotExplosions {
	public SlotCakeDynamite(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return super.isItemValid(itemstack) || (itemstack != null && itemstack.getItem() == ModItems.component && itemstack.getItemDamage() == ComponentTypes.DYNAMITE.getId());
	}
}