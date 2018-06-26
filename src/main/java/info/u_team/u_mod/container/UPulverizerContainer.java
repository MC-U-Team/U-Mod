package info.u_team.u_mod.container;

import info.u_team.u_mod.container.slots.*;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UPulverizerContainer extends UContainer {
	
	public EntityPlayer player;
	public World world;
	public BlockPos pos;
	
	public UPulverizerContainer(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.world = world;
		this.pos = pos;
		
		IInventory inventory = (IInventory) world.getTileEntity(pos);
		
		addSlotToContainer(new USlotOreInput(inventory, 0, 30, 23));
		addSlotToContainer(new USlotOutput(inventory, 1, 98, 54));
		addSlotToContainer(new USlotOutput(inventory, 2, 126, 54));
		addSlotToContainer(new USlotOutput(inventory, 3, 116, 24));

		appendPlayerInventory(player.inventory, 8, 84);
	}
	
	//TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
