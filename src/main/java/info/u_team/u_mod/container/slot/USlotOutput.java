package info.u_team.u_mod.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class USlotOutput extends Slot {
	
	public USlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
}
