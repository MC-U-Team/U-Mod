package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.tilentity.TileEntityBattery;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBattery extends BlockTileEntityBase {
	
	public BlockBattery(String name) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "battery_tile"), true, TileEntityBattery.class));
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isTranslucent(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean getUseNeighborBrightness(IBlockState state) {
		return false;
	}
}
