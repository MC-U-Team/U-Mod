package info.u_team.u_mod.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class SlotOreInput extends Slot {
	
	public SlotOreInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack);
	}
	
}
