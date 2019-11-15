package info.u_team.u_mod.block.basic;

import java.util.function.Supplier;

import info.u_team.u_mod.init.UModItemGroups;
import info.u_team.u_team_core.block.UTileEntityBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class BasicMachineBlock extends UTileEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public BasicMachineBlock(String name, Supplier<TileEntityType<?>> tileEntityType) {
		super(name, UModItemGroups.GROUP, Properties.create(Material.IRON), tileEntityType);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return openContainer(world, pos, player, true);
	}
	
	// Facing
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.toRotation(state.get(FACING)));
	}
	
}
