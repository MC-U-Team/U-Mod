package info.u_team.u_mod.energy;

import java.util.concurrent.atomic.AtomicInteger;

import info.u_team.u_mod.init.UModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.*;

public class EnergySystem {

	/**
	 * Pulls energy from the network
	 * 
	 * @return energy actually pulled from the network
	 */
	public static int pullEnergyFromNetwork(World world, BlockPos pos, int extract) {
		int energy = findEnergyFromSouroundings(world, pos, extract);
		if (energy <= 0)
			return extract;
		return discoverFurther(world, pos, pos, energy, 14) + (extract - energy);
	}

	/**
	 * Discover a pipe network with a certain depth
	 * 
	 * @return energy pulled from the network
	 */
	public static int discoverFurther(World world, BlockPos pos, BlockPos last, int extract, int depth) {
		int remaining = findEnergyFromSouroundings(world, pos, extract);
		if (depth == 1 || remaining <= 0)
			return extract;
		for (Direction direct : Direction.values()) {
			BlockPos newpos = pos.offset(direct);
			if (newpos.equals(last) || !world.getBlockState(newpos).getBlock().equals(UModBlocks.PIPE))
				continue;
			remaining -= discoverFurther(world, newpos, pos, remaining, depth - 1);
			if (remaining <= 0)
				return extract;
		}
		return extract - remaining;
	}

	/**
	 * Finds the nearest energy source to a specific position and extracts as much
	 * energy as needed or possible
	 * 
	 * @return energy remaining
	 */
	public static int findEnergyFromSouroundings(final World world, final BlockPos pos, final int extract) {
		final AtomicInteger integer = new AtomicInteger(extract);
		for (Direction direct : Direction.values()) {
			TileEntity entity = world.getTileEntity(pos.offset(direct));
			if (entity == null)
				continue;
			LazyOptional<IEnergyStorage> lazystorage = entity.getCapability(CapabilityEnergy.ENERGY,
					direct.getOpposite());
			lazystorage.filter(et -> et.canExtract()
					&& integer.getAndSet(integer.get() - et.extractEnergy(integer.get(), false)) > 0);
			if (integer.get() <= 0)
				return 0;
		}
		return integer.get();
	}

}
