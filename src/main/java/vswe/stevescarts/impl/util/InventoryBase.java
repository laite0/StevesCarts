package vswe.stevescarts.impl.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.collection.DefaultedList;

public class InventoryBase implements Inventory {

	private final int size;
	private DefaultedList<ItemStack> stacks;

	public InventoryBase(int size) {
		this.size = size;
		stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
	}

	public Tag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		Inventories.toTag(tag, stacks);
		return tag;
	}

	public void deserializeNBT(CompoundTag tag) {
		stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
		Inventories.fromTag(tag, stacks);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return stacks.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getStack(int slot) {
		return stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack stack = Inventories.splitStack(stacks, slot, amount);
		if (!stack.isEmpty()) {
			this.markDirty();
		}
		return stack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(stacks, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		stacks.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}

		this.markDirty();
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		stacks.clear();
	}

	public DefaultedList<ItemStack> getStacks() {
		return stacks;
	}
}
