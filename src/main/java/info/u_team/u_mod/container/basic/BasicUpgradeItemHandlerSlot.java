package info.u_team.u_mod.container.basic;

import info.u_team.u_mod.item.basic.BasicMachineUpgradeItem;
import info.u_team.u_team_core.container.ItemSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class BasicUpgradeItemHandlerSlot extends ItemSlot {
	
	public BasicUpgradeItemHandlerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof BasicMachineUpgradeItem;
	}
	
}
