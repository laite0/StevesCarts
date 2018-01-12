package vswe.stevescarts.modules.workers.tools;

import net.minecraft.item.ItemStack;
import vswe.stevescarts.SCConfig;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.helpers.ComponentTypes;

import javax.annotation.Nonnull;

public class ModuleWoodcutterHardened extends ModuleWoodcutter {
	public ModuleWoodcutterHardened(final EntityMinecartModular cart) {
		super(cart);
	}

	@Override
	public int getPercentageDropChance() {
		return 100;
	}

	@Override
	public int getMaxDurability() {
		return 640000;
	}

	@Override
	public String getRepairItemName() {
		return SCConfig.hardenedRepairName.isEmpty() ? ComponentTypes.REINFORCED_METAL.getLocalizedName(): SCConfig.hardenedRepairName;
	}

	@Override
	public int getRepairItemUnits(
		@Nonnull
			ItemStack item) {
		if (!item.isEmpty() && SCConfig.isValidRepairItem(item, "hardened")) {
			return 320000;
		}
		return 0;
	}

	@Override
	public int getRepairSpeed() {
		return 400;
	}
}
