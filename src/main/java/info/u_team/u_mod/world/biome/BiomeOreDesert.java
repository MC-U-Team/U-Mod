package info.u_team.u_mod.world.biome;

import java.util.Random;

import info.u_team.u_team_core.biome.UBiome;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BiomeOreDesert extends UBiome {
	
	public BiomeOreDesert(String name) {
		super(name, new BiomeProperties("Ore desert").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
		topBlock = Blocks.COBBLESTONE.getDefaultState(); // Only placeholder
		fillerBlock = Blocks.COBBLESTONE.getDefaultState();
		decorator.treesPerChunk = -999;
		decorator.deadBushPerChunk = 2;
		decorator.reedsPerChunk = 50;
		spawnableCreatureList.clear();
	}
	
	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		super.decorate(world, rand, pos);
		for (int i = 0; i < 5; i++) {
			GameRegistry.generateWorld(pos.getX() >> 4, pos.getZ() >> 4, world, world.provider.createChunkGenerator(), world.getChunkProvider());
		}
	}
}
