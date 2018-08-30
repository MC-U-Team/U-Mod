package info.u_team.u_mod.block.energy;

import info.u_team.u_mod.block.BlockEnergyGuiFacing;
import info.u_team.u_mod.container.machine.ContainerFurnace;
import info.u_team.u_mod.gui.machine.GuiFurnace;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.energy.TileEntityFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.*;

public class BlockFurnace extends BlockEnergyGuiFacing {
	
	public BlockFurnace(String name) {
		super(name, TileEntityFurnace.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(ContainerFurnace.class);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(GuiFurnace.class, id);
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
}
