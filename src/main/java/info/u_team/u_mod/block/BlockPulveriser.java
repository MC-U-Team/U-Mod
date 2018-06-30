package info.u_team.u_mod.block;

import java.util.List;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.ContainerPulverizer;
import info.u_team.u_mod.gui.GuiPulverizer;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.tilentity.TileEntityPulverizer;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class BlockPulveriser extends UBlockTileEntity {
	
	private int gui;
	
	public BlockPulveriser(String name) {
		super(name, Material.IRON, UCreativeTabs.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "pulverizer_tile"), true, TileEntityPulverizer.class));
		gui = UGuis.addGui(GuiPulverizer.class, ContainerPulverizer.class);
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
		
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityPulverizer) tileentity);
		worldIn.updateComparatorOutputLevel(pos, this);
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
