package info.u_team.u_mod.container.basic;

import info.u_team.u_team_core.container.ItemSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.*;

public class BasicOutputItemHandlerSlot extends ItemSlot {
	
	public BasicOutputItemHandlerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
}
