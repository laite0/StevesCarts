package vswe.stevescarts.helpers.storages;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.client.RenderUtil;
import vswe.stevescarts.guis.GuiBase;
import vswe.stevescarts.helpers.Localization;

import java.text.NumberFormat;

@Deprecated
public class SCTank extends FluidTank {
	private ITankHolder owner;
	private int tankid;
	private boolean isLocked;

	public SCTank(final ITankHolder owner, final int tankSize, final int tankid) {
		super(tankSize);
		this.owner = owner;
		this.tankid = tankid;
	}

	public SCTank copy() {
		final SCTank tank = new SCTank(owner, capacity, tankid);
		if (getFluid() != null) {
			tank.setFluid(getFluid().copy());
		}
		return tank;
	}

	public void containerTransfer() {
		ItemStack item = owner.getInputContainer(tankid);
		if (!item.isEmpty()) {
			IFluidHandler handler = FluidUtil.getFluidHandler(item);
			if (handler != null) {
				FluidStack fluidStack = handler.drain(Fluid.BUCKET_VOLUME, false);
				if (fluidStack != null && fluidStack.amount >= Fluid.BUCKET_VOLUME) {
					FluidActionResult result = FluidUtil.tryEmptyContainer(item, this, Fluid.BUCKET_VOLUME, null, false);
					if (result.isSuccess()) {
						ItemStack container = result.getResult();
						handler = FluidUtil.getFluidHandler(container);
						if (handler != null) {
							fluidStack = handler.drain(Fluid.BUCKET_VOLUME, false);
							if (fluidStack != null && fluidStack.amount == Fluid.BUCKET_VOLUME) {
								FluidUtil.tryEmptyContainer(item, this, Fluid.BUCKET_VOLUME, null, true);
								owner.setInputContainer(tankid, container);
								return;
							}
						}
						if (!container.isEmpty()) {
							owner.addToOutputContainer(tankid, container);
						}
						if (container.getCount() == 0) {
							FluidUtil.tryEmptyContainer(item, this, Fluid.BUCKET_VOLUME, null, true);

							item.shrink(1);
							if (item.isEmpty() || item.getCount() <= 0)
								owner.setInputContainer(tankid, ItemStack.EMPTY);
						}
					}
				} else {
					FluidActionResult result = FluidUtil.tryFillContainer(item, this, Fluid.BUCKET_VOLUME, null, false);
					if (result.isSuccess()) {
						ItemStack container = result.getResult();
						if (!container.isEmpty()) {
							owner.addToOutputContainer(tankid, container);
							if (container.getCount() == 0) {
								FluidUtil.tryFillContainer(item, this, Fluid.BUCKET_VOLUME, null, true);

								item.shrink(1);
								if (item.isEmpty() || item.getCount() <= 0)
									owner.setInputContainer(tankid, ItemStack.EMPTY);
							}
						}
					}
				}
			}
		}
	}

	public void setLocked(final boolean val) {
		isLocked = val;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public String getMouseOver() {
		String name = Localization.MODULES.TANKS.EMPTY.translate();
		int amount = 0;
		if (fluid != null) {
			name = fluid.getFluid().getLocalizedName(fluid);
			if (name.indexOf(".") != -1) {
				name = FluidRegistry.getFluidName(fluid);
			}
			if (name != null && !name.equals("")) {
				name = fluid.getFluid().getRarity().rarityColor.toString() + Character.toUpperCase(name.charAt(0)) + name.substring(1) + TextFormatting.WHITE;
			} else {
				name = Localization.MODULES.TANKS.INVALID.translate();
			}
			amount = fluid.amount;
		}
		NumberFormat format = NumberFormat.getInstance();
		return name + "\n" + format.format(amount) + " / " + format.format(capacity);
	}

	private static float getColorComponent(final int color, final int id) {
		return ((color & 255 << id * 8) >> id * 8) / 255.0f;
	}

	@SideOnly(Side.CLIENT)
	public void drawFluid(final GuiBase gui, final int startX, final int startY, int width, int height) {
		RenderUtil.renderGuiTank(this, gui.getGuiLeft() + startX, + gui.getGuiTop() + startY, 0, width, height);
	}

	@Override
	public FluidTankInfo getInfo() {
		return new FluidTankInfo(fluid, getCapacity());
	}
}
