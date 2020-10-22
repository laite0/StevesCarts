package vswe.stevescarts.impl.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import vswe.stevescarts.impl.entity.CartEntity;

public class CartScreenHandler extends ScreenHandler {

	public final PlayerInventory playerInventory;
	public final CartEntity cartEntity;

	public CartScreenHandler(int syncID, PlayerEntity playerEntity, CartEntity cartEntity) {
		super(null, syncID);
		this.playerInventory = playerEntity.inventory;
		this.cartEntity = cartEntity;

		for(int x = 0; x < 3; ++x) {
			for(int y = 0; y < 9; ++y) {
				this.addSlot(new Slot(playerInventory, y + (x + 1) * 9, 8 + y * 18, 84 + x * 18));
			}
		}

		for(int x = 0; x < 9; ++x) {
			this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
		}

		int s = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				this.addSlot(new Slot(cartEntity.inventory, s++, 97 + (i * 18), 8 + (j * 18)));
			}

		}

	}

	@Override
	public boolean canUse(PlayerEntity var1) {
		return true;
	}
}
