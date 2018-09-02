package info.u_team.u_mod.block;

import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockTileEntityBase extends UBlockTileEntity {
	
	public BlockTileEntityBase(String name, UTileEntityProvider provider) {
		super(name, Material.IRON, UCreativeTabs.machine, provider);
	}
	
	// TODO
	// Currently drop all things. Its planned to store nbt tags on stacks when
	// breaking and restore them when placing
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
		worldIn.updateComparatorOutputLevel(pos, this);
		
		super.breakBlock(worldIn, pos, state);
	}
	
}
