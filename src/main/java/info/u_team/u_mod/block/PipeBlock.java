package info.u_team.u_mod.block;

import java.util.Arrays;

import info.u_team.u_mod.energy.EnergySystem;
import info.u_team.u_mod.init.UModItemGroups;
import info.u_team.u_team_core.block.UBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class PipeBlock extends UBlock {

	public PipeBlock(String name) {
		super(name, UModItemGroups.GROUP, Properties.create(Material.IRON));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		Arrays.stream(Direction.values()).forEach(dir -> onNeighborChange(state, worldIn, pos, pos.offset(dir)));
	}

	@Override
	public boolean canBeConnectedTo(BlockState state, IBlockReader world, BlockPos pos, Direction facing) {
		TileEntity entity = world.getTileEntity(pos);
		return entity != null && entity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).isPresent();
	}

	// TODO AABB

	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		if(world.isRemote()) return;
		TileEntity entity = world.getTileEntity(neighbor);
 		if (entity == null) {
			if(EnergySystem.POSITIONS.contains(neighbor))
				EnergySystem.POSITIONS.remove(neighbor);
			return;
		}
		if(checkIsOutput(entity, offset(pos, neighbor))) {
			if(!EnergySystem.POSITIONS.contains(neighbor))
				EnergySystem.POSITIONS.add(neighbor);
		}
	}
	
	private boolean checkIsOutput(TileEntity entity, Direction dir) {
		return entity.getCapability(CapabilityEnergy.ENERGY, dir)
				.filter(store -> store.canReceive()).isPresent();
	}

	// This gets the direction between 2 vectors
	private Direction offset(BlockPos pos, BlockPos neighbor) {
		BlockPos delta = pos.subtract(neighbor); /* TODO there has to be a better way */
		return Direction.getFacingFromVector(delta.getX(), delta.getY(), delta.getZ());
	}
}
