package vswe.stevescarts.modules.workers.tools;

import net.minecraft.item.ItemStack;
import vswe.stevescarts.SCConfig;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.helpers.Localization;

import javax.annotation.Nonnull;

public class ModuleDrillIron extends ModuleDrill {
	public ModuleDrillIron(final EntityMinecartModular cart) {
		super(cart);
	}

	@Override
	protected int blocksOnTop() {
		return 3;
	}

	@Override
	protected int blocksOnSide() {
		return 1;
	}

	@Override
	protected float getTimeMult() {
		return 40.0f;
	}

	@Override
	public int getMaxDurability() {
		return 50000;
	}

	@Override
	public String getRepairItemName() {
		return SCConfig.ironRepairName.isEmpty() ? Localization.MODULES.TOOLS.IRON.translate(): SCConfig.ironRepairName;
	}

	@Override
	public int getRepairItemUnits(@Nonnull ItemStack item) {
		if (!item.isEmpty() && SCConfig.isValidRepairItem(item, "iron")) {
			return 20000;
		}
		return 0;
	}

	@Override
	public int getRepairSpeed() {
		return 50;
	}

	@Override
	public boolean useDurability() {
		return true;
	}
}
