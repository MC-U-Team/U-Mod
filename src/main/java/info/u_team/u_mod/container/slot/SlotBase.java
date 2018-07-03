package info.u_team.u_mod.container.slot;

import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.resource.EnumModeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotBase extends Slot {
	
	public SlotBase(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
		
}
