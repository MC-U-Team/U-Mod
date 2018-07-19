package info.u_team.u_mod.world.biome;

import java.util.*;

import info.u_team.u_mod.init.UGeneration;
import info.u_team.u_mod.resource.ResourceUtil;
import info.u_team.u_team_core.biome.UBiome;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeOreDesert extends UBiome {
	
	private List<IBlockState> states = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public BiomeOreDesert(String name) {
		super(name, new BiomeProperties("Ore Desert").setBaseHeight(0.45F).setHeightVariation(0.45F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled());
		topBlock = Blocks.STONE.getDefaultState();
		fillerBlock = Blocks.STONE.getDefaultState();
		decorator.treesPerChunk = -999;
		decorator.deadBushPerChunk = 0;
		decorator.reedsPerChunk = 0;
		spawnableCreatureList.clear();
		
		ResourceUtil.iterate(resource -> states.add(resource.getOreBlockState()));
		
		for (int i = 0; i < 50; i++) {
			states.add(Blocks.STONE.getStateFromMeta(BlockStone.EnumType.STONE.getMetadata()));
		}
		states.add(Blocks.COAL_ORE.getDefaultState());
		states.add(Blocks.IRON_ORE.getDefaultState());
		states.add(Blocks.GOLD_ORE.getDefaultState());
		states.add(Blocks.DIAMOND_ORE.getDefaultState());
		states.add(Blocks.EMERALD_ORE.getDefaultState());
		
	}
	
	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		super.decorate(world, rand, pos);
		for (int i = 0; i < 9; i++) {
			UGeneration.worldgeneration.generateOres(pos, world, rand);
		}
	}
	
	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkprimer, int x, int z, double noiseVal) {
		int i = world.getSeaLevel();
		IBlockState iblockstate = topBlock;
		IBlockState iblockstate1 = fillerBlock;
		int j = -1;
		int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = x & 15;
		int i1 = z & 15;
		BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
		
		iblockstate = states.get(rand.nextInt(states.size()));
		
		for (int j1 = 255; j1 >= 0; --j1) {
			if (j1 <= rand.nextInt(5)) {
				chunkprimer.setBlockState(i1, j1, l, BEDROCK);
			} else {
				IBlockState iblockstate2 = chunkprimer.getBlockState(i1, j1, l);
				
				if (iblockstate2.getMaterial() == Material.AIR) {
					j = -1;
				} else if (iblockstate2.getBlock() == Blocks.STONE) {
					if (j == -1) {
						if (k <= 0) {
							iblockstate = AIR;
							iblockstate1 = STONE;
						} else if (j1 >= i - 4 && j1 <= i + 1) {
							iblockstate = topBlock = states.get(rand.nextInt(states.size()));
							iblockstate1 = this.fillerBlock;
						}
						
						if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
							if (this.getTemperature(mutableblockpos.setPos(x, j1, z)) < 0.15F) {
								iblockstate = ICE;
							} else {
								iblockstate = WATER;
							}
						}
						
						j = k;
						
						if (j1 >= i - 1) {
							chunkprimer.setBlockState(i1, j1, l, iblockstate);
						} else if (j1 < i - 7 - k) {
							iblockstate = AIR;
							iblockstate1 = STONE;
							chunkprimer.setBlockState(i1, j1, l, GRAVEL);
						} else {
							chunkprimer.setBlockState(i1, j1, l, iblockstate1);
						}
					} else if (j > 0) {
						--j;
						chunkprimer.setBlockState(i1, j1, l, iblockstate1);
					}
				}
			}
		}
	}
}
