package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.tilentity.TileEntityBattery;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockBattery extends BlockEnergy {
	
	public BlockBattery(String name) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "battery_tile"), true, TileEntityBattery.class));
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean getUseNeighborBrightness(IBlockState state) {
		return false;
	}
}
