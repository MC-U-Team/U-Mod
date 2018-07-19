package info.u_team.u_mod.block.machine;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.machine.ContainerEnricher;
import info.u_team.u_mod.gui.machine.GuiEnricher;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.machine.TileEntityEnricher;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnricher extends BlockMaschine {
	
	private int gui;
	
	public BlockEnricher(String name) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "enricher_tile"), true, TileEntityEnricher.class));
		gui = UGuis.addGui(GuiEnricher.class, ContainerEnricher.class);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(UConstants.MODID, gui, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
		worldIn.updateComparatorOutputLevel(pos, this);
		
		super.breakBlock(worldIn, pos, state);
	}
	
}
