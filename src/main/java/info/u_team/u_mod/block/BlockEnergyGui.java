package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.tileentity.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.*;

public abstract class BlockEnergyGui extends BlockTileEntityBase {
	
	protected int gui;
	
	public BlockEnergyGui(String name, Class<? extends UTileEntity> tileentity) {
		this(name, tileentity, true);
	}
	
	public BlockEnergyGui(String name, Class<? extends UTileEntity> tileentity, boolean register) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, name + "_tile"), register, tileentity));
		gui = getContainer();
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			getGui(gui);
		}
	}
	
	protected abstract int getContainer();
	
	@SideOnly(Side.CLIENT)
	protected abstract void getGui(int id);
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		player.openGui(UConstants.MODID, gui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	// Do we really need to set all that here? Maybe not all sub blocks need this
	
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
