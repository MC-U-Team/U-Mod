package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_team_core.registry.BlockRegistry;

public class UModBlocks {
	
	
	
	public static void construct() {
		BlockRegistry.register(UMod.modid, UModBlocks.class);
	}
	
}
