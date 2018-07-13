package info.u_team.u_mod.container;

import info.u_team.u_mod.container.slot.SlotOreInput;
import info.u_team.u_mod.container.slot.SlotOutput;
import info.u_team.u_mod.resource.EnumModeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerPulverizer extends ContainerBase {
	
	public ContainerPulverizer(EntityPlayer player, World world, BlockPos pos) {
		super(player, world,  pos);
		addSlot(EnumModeTab.NORMAL, new SlotOreInput(tile, 0, 30, 23));
		addSlot(EnumModeTab.NORMAL, new SlotOutput(tile, 1, 98, 54));
		addSlot(EnumModeTab.NORMAL, new SlotOutput(tile, 2, 126, 54));
		addSlot(EnumModeTab.NORMAL, new SlotOutput(tile, 3, 116, 24));
	}
	
	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
