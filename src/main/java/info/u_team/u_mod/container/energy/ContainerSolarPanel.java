package info.u_team.u_mod.container.energy;

import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.container.slot.SlotBase;
import info.u_team.u_mod.resource.EnumModeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSolarPanel extends ContainerBase {
	
	public ContainerSolarPanel(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
		addSlot(EnumModeTab.NORMAL, new SlotBase(tile, 0, 80, 34));
	}
	
	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
