package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.UContainerPulverizer;
import info.u_team.u_mod.gui.UGuiPulverizer;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.tilentity.UTileEntityPulverizer;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class UBlockPulveriser extends UBlockTileEntity {
	
	private int gui;
	
	public UBlockPulveriser(String name) {
		super(name, Material.IRON, UCreativeTabs.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "pulverizer_tile"), true, UTileEntityPulverizer.class));
		gui = UGuis.addGui(UGuiPulverizer.class, UContainerPulverizer.class);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(UConstants.MODID, gui, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
