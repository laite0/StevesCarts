package vswe.stevescarts.impl.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import vswe.stevescarts.impl.entity.CartEntity;

public class InventoryUtils {

	public static ItemStack insertItemStacked(Inventory inventory, ItemStack input, boolean simulate) {
		ItemStack stack = input.copy();
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack targetStack = inventory.getStack(i);

			//Nice and simple, insert the item into a blank slot
			if(targetStack.isEmpty()){
				if(!simulate){
					inventory.setStack(i, stack);
				}
				return ItemStack.EMPTY;
			} else if (ItemUtils.isItemEqual(stack, targetStack, true, false)){
				int freeStackSpace = targetStack.getMaxCount() - targetStack.getCount();
				if(freeStackSpace > 0){
					int transferAmount = Math.min(freeStackSpace, input.getCount());
					if(!simulate){
						targetStack.increment(transferAmount);
					}
					stack.decrement(transferAmount);
				}
			}
		}
		return stack;
	}

	public static ItemStack insertItem(ItemStack input, BlockEntity blockEntity, Direction direction){
		ItemStack stack = input.copy();

		if(blockEntity instanceof SidedInventory){
			SidedInventory sidedInventory = (SidedInventory) blockEntity;
			for(int slot : sidedInventory.getAvailableSlots(direction)){
				if(sidedInventory.canInsert(slot, stack, direction)){
					stack = insertIntoInv(sidedInventory, slot, stack);
					if(stack.isEmpty()){
						break;
					}
				}
			}
			return stack;
		} else if(blockEntity instanceof Inventory){
			Inventory inventory = (Inventory) blockEntity;
			for (int i = 0; i < inventory.size() & !stack.isEmpty(); i++) {
				stack = insertIntoInv(inventory, i, stack);
			}
		}
		return stack;
	}

	public static ItemStack insertItem(ItemStack input, CartEntity cartEntity) {
		ItemStack stack = input.copy();
		for (int i = 0; i < cartEntity.inventory.size(); i++) {
			stack = insertIntoInv(cartEntity.inventory, i, stack);
		}
		return stack;
	}

	private static ItemStack insertIntoInv(Inventory inventory, int slot, ItemStack input){
		ItemStack targetStack = inventory.getStack(slot);
		ItemStack stack = input.copy();

		//Nice and simple, insert the item into a blank slot
		if(targetStack.isEmpty()){
			inventory.setStack(slot, stack);
			return ItemStack.EMPTY;
		} else if (ItemUtils.isItemEqual(stack, targetStack, true, false)){
			int freeStackSpace = targetStack.getMaxCount() - targetStack.getCount();
			if(freeStackSpace > 0){
				int transferAmount = Math.min(freeStackSpace, stack.getCount());
				targetStack.increment(transferAmount);
				stack.decrement(transferAmount);
			}
		}

		return stack;
	}
}
