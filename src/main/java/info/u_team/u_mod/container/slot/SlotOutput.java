package info.u_team.u_mod.container.slot;

import info.u_team.u_mod.resource.EnumModeTab;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class SlotOutput extends SlotBase{
	
	public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
}
