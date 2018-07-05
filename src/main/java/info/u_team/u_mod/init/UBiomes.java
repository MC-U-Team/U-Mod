package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.world.biome.BiomeContamined;
import info.u_team.u_team_core.biome.UBiome;
import info.u_team.u_team_core.registry.BiomeRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.world.biome.Biome;

public class UBiomes {
	
	public static final UBiome contamined = new BiomeContamined("contamined");
	
	public static void preinit() {
		BiomeRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Biome.class, UBiomes.class));
	}

	public static void init() {
		
	}
	
}
