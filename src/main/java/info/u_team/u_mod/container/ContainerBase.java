package info.u_team.u_mod.container;

import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerBase extends UContainer{
	
	protected UTileEntity entity;
	
	public ContainerBase(EntityPlayer player, World world, BlockPos pos) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, (IInventory) this.entity);
	}
	
}
