package vswe.stevescarts.modules.workers.tools;

import net.minecraft.item.ItemStack;
import vswe.stevescarts.SCConfig;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.helpers.Localization;

import javax.annotation.Nonnull;

public class ModuleFarmerDiamond extends ModuleFarmer {
	public ModuleFarmerDiamond(final EntityMinecartModular cart) {
		super(cart);
	}

	@Override
	public int getMaxDurability() {
		return 300000;
	}

	@Override
	public String getRepairItemName() {
		return SCConfig.diamondRepairName.isEmpty() ? Localization.MODULES.TOOLS.DIAMONDS.translate(): SCConfig.diamondRepairName;
	}

	@Override
	public int getRepairItemUnits(@Nonnull ItemStack item) {
		if (!item.isEmpty() && SCConfig.isValidRepairItem(item, "diamond")) {
			return 150000;
		}
		return 0;
	}

	@Override
	public boolean useDurability() {
		return true;
	}

	@Override
	public int getRepairSpeed() {
		return 500;
	}

	@Override
	public int getRange() {
		return 1;
	}
}
