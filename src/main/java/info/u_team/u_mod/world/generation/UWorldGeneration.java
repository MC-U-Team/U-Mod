package info.u_team.u_mod.world.generation;

import java.util.Random;

import com.google.common.base.Predicate;

import info.u_team.u_mod.config.UConfigWorldGeneration;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre.GenerationType;
import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.resource.EnumResources1;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class UWorldGeneration implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos chunkpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		DimensionType dimension = world.provider.getDimensionType();
		
		if (dimension == DimensionType.OVERWORLD) {
			// Ores
			generateOreFromConfig(UConfigWorldGeneration.oreAluminium, EnumResources1.ALUMINUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
		}
	}
	
	private void generateOreFromConfig(GeneratableOre configore, IBlockState state, BlockPos pos, World world, Random random) {
		if (configore.type == UConfigWorldGeneration.GeneratableOre.GenerationType.MINMAX) {
			generateOreMinMax(state, pos, world, random, configore.count, configore.chance, configore.veinsize, configore.height1, configore.height2);
		} else if (configore.type == GenerationType.CENTERSPREAD) {
			generateOreCenterSpread(state, pos, world, random, configore.count, configore.chance, configore.veinsize, configore.height1, configore.height2);
		}
	}
	
	private void generateOreMinMax(IBlockState state, BlockPos pos, World world, Random random, int count, double chance, int veinsize, int min, int max) {
		generateOreMinMax(state, pos, world, random, getStoneReplacable(), count, chance, veinsize, min, max);
	}
	
	private void generateOreCenterSpread(IBlockState state, BlockPos pos, World world, Random random, int count, double chance, int veinsize, int center, int spread) {
		generateOreCenterSpread(state, pos, world, random, getStoneReplacable(), count, chance, veinsize, center, spread);
	}
	
	private void generateOreMinMax(IBlockState state, BlockPos pos, World world, Random random, Predicate<IBlockState> allowed, int count, double chance, int veinsize, int min, int max) {
		if (max < min) {
			int i = min;
			min = max;
			max = i;
		} else if (max == min) {
			if (min < 255) {
				++max;
			} else {
				--min;
			}
		}
		WorldGenMinable generator = new WorldGenMinable(state, veinsize, allowed);
		for (int i = 0; i < count; i++) {
			if (random.nextDouble() <= chance) {
				generator.generate(world, random, pos.add(random.nextInt(16), random.nextInt(max - min) + min, random.nextInt(16)));
			}
		}
	}
	
	private void generateOreCenterSpread(IBlockState state, BlockPos pos, World world, Random random, Predicate<IBlockState> allowed, int count, double chance, int veinsize, int center, int spread) {
		WorldGenMinable generator = new WorldGenMinable(state, veinsize, allowed);
		for (int i = 0; i < count; i++) {
			if (random.nextDouble() <= chance) {
				generator.generate(world, random, pos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + center - spread, random.nextInt(16)));
			}
		}
	}
	
	private Predicate<IBlockState> getStoneReplacable() {
		return (input) -> {
			return (input != null && input.getBlock() == Blocks.STONE && input.getValue(BlockStone.VARIANT).isNatural());
		};
	}
	
	// Enable if needed
	// private Predicate<IBlockState> getBlockReplacable(Block block) {
	// return (input) -> {
	// return input != null && input.getBlock() == block;
	// };
	// }
	
}
