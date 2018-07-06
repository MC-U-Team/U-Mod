package info.u_team.u_mod.world.biome;

import info.u_team.u_team_core.biome.UBiome;
import net.minecraft.init.Blocks;

public class BiomeContamined extends UBiome {
	
	public BiomeContamined(String name) {
		super(name);
		topBlock = Blocks.COBBLESTONE.getDefaultState();
	}
	
}
