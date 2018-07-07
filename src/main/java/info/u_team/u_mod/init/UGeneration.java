package info.u_team.u_mod.init;

import info.u_team.u_mod.world.generation.UWorldGeneration;
import info.u_team.u_team_core.registry.CommonRegistry;

public class UGeneration {
	
	public static final UWorldGeneration WORLDGENERATION = new UWorldGeneration();
	
	public static void init() {
		CommonRegistry.registerWorldGeneration(WORLDGENERATION, 0);
	}
	
}
