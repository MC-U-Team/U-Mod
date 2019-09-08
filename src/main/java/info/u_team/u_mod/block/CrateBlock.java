package info.u_team.u_mod.block;

import info.u_team.u_mod.init.*;
import info.u_team.u_mod.type.Crate;
import info.u_team.u_team_core.block.UTileEntityBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;

public class CrateBlock extends UTileEntityBlock {
	
	public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
	
	private final Crate crate;
	
	public CrateBlock(Crate crate, String name) {
		super(crate.getName() + "_" + name, UModItemGroups.GROUP, Properties.create(Material.WOOD), () -> UModTileEntityTypes.CRATE);
		this.crate = crate;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(DIRECTION);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(DIRECTION, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	public Crate getCrate() {
		return crate;
	}
	
}
