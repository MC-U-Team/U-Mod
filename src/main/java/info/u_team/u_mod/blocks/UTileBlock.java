package info.u_team.u_mod.blocks;

import javax.annotation.Nullable;

import info.u_team.u_mod.*;
import info.u_team.u_mod.container.UContainer;
import info.u_team.u_mod.gui.IUGui;
import info.u_team.u_mod.tileentitys.UTileEntityProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UTileBlock extends UBlock implements ITileEntityProvider {
	
	private UTileEntityProvider _provider;
	private int _uiid = -1;
	
	public UTileBlock(Material materialIn, String name, UTileEntityProvider provider) {
		super(materialIn, name);
		this._provider = provider;
	}
	
	public UTileBlock(Material materialIn, String name, UTileEntityProvider provider, Class<? extends IUGui> gui, @Nullable Class<? extends UContainer> container) {
		super(materialIn, name);
		this._provider = provider;
		this._uiid = UGuihandler.addGui(gui, container);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return _provider.create(worldIn, meta);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this.hasUI()) {
			playerIn.openGui(UConstants.MODID, this._uiid, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	public boolean hasUI() {
		return _uiid > -1;
	}
	
	public int getUIID() {
		return _uiid;
	}
}
