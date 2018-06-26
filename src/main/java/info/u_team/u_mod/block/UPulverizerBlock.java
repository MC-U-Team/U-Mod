package info.u_team.u_mod.block;

import info.u_team.u_mod.*;
import info.u_team.u_mod.container.UPulverizerContainer;
import info.u_team.u_mod.gui.UPulverizerGui;
import info.u_team.u_mod.tilentity.UPulverizerTile;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class UPulverizerBlock extends UBlockTileEntity {
	
	private int _uiid;
	
	public UPulverizerBlock(String name) {
		super(name, Material.IRON, UConstants.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "pulverizer_tile"), true, UPulverizerTile.class));
		setRegistryName(new ResourceLocation(UConstants.MODID, name));
		setUnlocalizedName(name);
		_uiid = UGuihandler.addGui(UPulverizerGui.class, UPulverizerContainer.class);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(UConstants.MODID, _uiid, worldIn, pos.getX(), pos.getY(), pos.getZ());
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
