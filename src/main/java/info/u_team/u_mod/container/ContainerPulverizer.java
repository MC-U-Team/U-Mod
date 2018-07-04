package info.u_team.u_mod.container;

import info.u_team.u_mod.container.slot.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerPulverizer extends ContainerBase {
	
	public ContainerPulverizer(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
		addSlotToContainer(new SlotOreInput(tile, 0, 30, 23));
		addSlotToContainer(new SlotOutput(tile, 1, 98, 54));
		addSlotToContainer(new SlotOutput(tile, 2, 126, 54));
		addSlotToContainer(new SlotOutput(tile, 3, 116, 24));
		
		appendPlayerInventory(player.inventory, 8, 84);
	}
	
	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
