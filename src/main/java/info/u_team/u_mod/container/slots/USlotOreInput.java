package info.u_team.u_mod.container.slots;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class USlotOreInput extends Slot {
	
	public USlotOreInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack);
	}
	
}
