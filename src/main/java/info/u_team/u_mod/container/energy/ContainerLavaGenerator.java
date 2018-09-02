package info.u_team.u_mod.container.energy;

import info.u_team.u_mod.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerLavaGenerator extends ContainerBase {
	
	public ContainerLavaGenerator(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
	}
	
	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
