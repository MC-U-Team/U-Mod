package info.u_team.u_mod.container.energy;

import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.container.slot.*;
import info.u_team.u_mod.resource.EnumModeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerAlloyFurnace extends ContainerBase {
	
	public ContainerAlloyFurnace(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
		addSlot(EnumModeTab.NORMAL, new SlotOreInput(tile, 0, 51, 7));
		addSlot(EnumModeTab.NORMAL, new SlotOreInput(tile, 1, 80, 7));
		addSlot(EnumModeTab.NORMAL, new SlotOreInput(tile, 2, 109, 7));
		addSlot(EnumModeTab.NORMAL, new SlotOutput(tile, 3, 80, 55));
	}
	
	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
