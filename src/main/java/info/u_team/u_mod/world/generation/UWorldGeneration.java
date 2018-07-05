package info.u_team.u_mod.world.generation;

import java.util.Random;

import info.u_team.u_mod.config.UConfigWorldGeneration;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre.GenerationType;
import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.resource.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class UWorldGeneration implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos chunkpos = new BlockPos(chunkX * 16, 0, chunkZ * 16); // Not shift the position here, cause WorldGenMinable is doing this already.
		DimensionType dimension = world.provider.getDimensionType();
		
		if (dimension == DimensionType.OVERWORLD) {
			
			// Ores
			// 1
			generateOreFromConfig(UConfigWorldGeneration.oreAluminium, EnumResources1.ALUMINUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreBeryllium, EnumResources1.BERYLLIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreCadmium, EnumResources1.CADMIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreChromium, EnumResources1.CHROMIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreCobalt, EnumResources1.COBALT.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreCopper, EnumResources1.COPPER.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreGallium, EnumResources1.GALLIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreGraphite, EnumResources1.GRAPHITE.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreIridium, EnumResources1.IRIDIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreLead, EnumResources1.LEAD.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreMagnesium, EnumResources1.MAGNESIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreMolybdenum, EnumResources1.MOLYBDENUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreNickel, EnumResources1.NICKEL.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.orePalladium, EnumResources1.PALLADIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.orePlatinum, EnumResources1.PLATINUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.orePlutonium, EnumResources1.PLUTONIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			// 2
			generateOreFromConfig(UConfigWorldGeneration.orePotassium, EnumResources2.POTASSIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreSilver, EnumResources2.SILVER.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreSodium, EnumResources2.SODIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreTantalum, EnumResources2.TANTALUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreTin, EnumResources2.TIN.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreTungsten, EnumResources2.TUNGSTEN.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreUranium, EnumResources2.URANIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreVanadium, EnumResources2.VANADIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreZinc, EnumResources2.ZINC.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(UConfigWorldGeneration.oreZirconium, EnumResources2.ZIRCONIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			
		}
	}
	
	private void generateOreFromConfig(GeneratableOre configore, IBlockState state, BlockPos pos, World world, Random random) {
		if (!configore.enabled) {
			return;
		}
		if (configore.type == GenerationType.MINMAX) {
			generateOreMinMax(state, pos, world, random, configore.count, configore.chance, configore.veinsize, configore.height1, configore.height2);
		} else if (configore.type == GenerationType.CENTERSPREAD) {
			generateOreCenterSpread(state, pos, world, random, configore.count, configore.chance, configore.veinsize, configore.height1, configore.height2);
		}
	}
	
	private void generateOreMinMax(IBlockState state, BlockPos pos, World world, Random random, int count, double chance, int veinsize, int min, int max) {
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
		WorldGenMinable generator = new WorldGenMinable(state, veinsize);
		for (int i = 0; i < count; i++) {
			if (random.nextDouble() <= chance) {
				BlockPos randpos = pos.add(random.nextInt(16), random.nextInt(max - min) + min, random.nextInt(16));
				generator.generate(world, random, randpos);
			}
		}
	}
	
	private void generateOreCenterSpread(IBlockState state, BlockPos pos, World world, Random random, int count, double chance, int veinsize, int center, int spread) {
		WorldGenMinable generator = new WorldGenMinable(state, veinsize);
		for (int i = 0; i < count; i++) {
			if (random.nextDouble() <= chance) {
				BlockPos randpos = pos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + center - spread, random.nextInt(16));
				generator.generate(world, random, randpos);
			}
		}
	}
	
}
