package info.u_team.u_mod.init;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.world.biome.BiomeContamined;
import info.u_team.u_team_core.biome.UBiome;
import info.u_team.u_team_core.registry.BiomeRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.*;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class UBiomes {
	
	public static final UBiome contamined = new BiomeContamined("contamined");
	
	public static void preinit() {
		BiomeRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Biome.class, UBiomes.class));
	}
	
	public static void init() {
		addOverworldSpawn(contamined, BiomeType.WARM, 1000, WET, FOREST, HILLS);
	}
	
	private static void addOverworldSpawn(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));
	}
}
