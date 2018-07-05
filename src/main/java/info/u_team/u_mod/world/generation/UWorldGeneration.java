package info.u_team.u_mod.world.generation;

import static info.u_team.u_mod.config.UConfigWorldGeneration.*;
import static info.u_team.u_mod.resource.EnumResources1.*;
import static info.u_team.u_mod.resource.EnumResources2.*;

import java.util.Random;

import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre;
import info.u_team.u_mod.config.UConfigWorldGeneration.GeneratableOre.GenerationType;
import info.u_team.u_mod.init.UBlocks;
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
		if (!enabled) {
			return;
		}
		BlockPos chunkpos = new BlockPos(chunkX * 16, 0, chunkZ * 16); // Not shifting the position here, cause WorldGenMinable is doing this already.
		DimensionType dimension = world.provider.getDimensionType();
		
		if (dimension == DimensionType.OVERWORLD) {
			
			if (!overworld.enabled) {
				return;
			}
			
			// Ores
			// 1
			generateOreFromConfig(overworld.oreAluminium, ALUMINUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreBeryllium, BERYLLIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreCadmium, CADMIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreChromium, CHROMIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreCobalt, COBALT.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreCopper, COPPER.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreGallium, GALLIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreGraphite, GRAPHITE.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreIridium, IRIDIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreLead, LEAD.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreMagnesium, MAGNESIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreMolybdenum, MOLYBDENUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.oreNickel, NICKEL.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.orePalladium, PALLADIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.orePlatinum, PLATINUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			generateOreFromConfig(overworld.orePlutonium, PLUTONIUM.getBlockState(UBlocks.resource_ore1), chunkpos, world, random);
			// 2
			generateOreFromConfig(overworld.orePotassium, POTASSIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreSilver, SILVER.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreSodium, SODIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreTantalum, TANTALUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreTin, TIN.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreTungsten, TUNGSTEN.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreUranium, URANIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreVanadium, VANADIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreZinc, ZINC.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			generateOreFromConfig(overworld.oreZirconium, ZIRCONIUM.getBlockState(UBlocks.resource_ore2), chunkpos, world, random);
			
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
