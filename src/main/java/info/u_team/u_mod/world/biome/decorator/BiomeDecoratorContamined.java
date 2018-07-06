package info.u_team.u_mod.world.biome.decorator;

import java.util.Random;

import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.resource.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeDecoratorContamined extends BiomeDecorator {
	
	@Override
	public void decorate(World world, Random random, Biome biome, BlockPos pos) {
		super.decorate(world, random, biome, pos);
		genStandardOre1(world, random, 30, new WorldGenMinable(EnumResources2.URANIUM.getBlockState(UBlocks.resource_ore2), 15), 0, 70);
		genStandardOre1(world, random, 10, new WorldGenMinable(EnumResources1.PLUTONIUM.getBlockState(UBlocks.resource_ore1), 8), 0, 70);
		if (random.nextDouble() <= 0.75) {
			int randX = random.nextInt(16) + 8;
			int randZ = random.nextInt(16) + 8;
			BlockPos randPos = world.getTopSolidOrLiquidBlock(pos.add(randX, 0, randZ));
			world.setBlockState(randPos, EnumResources2.URANIUM.getBlockState(UBlocks.resource_ore2), 2);
		}
	}
	
}
