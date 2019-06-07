package info.u_team.u_mod.proxy;

import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.api.IModProxy;
import info.u_team.u_team_core.intern.config.ClientConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class CommonProxy implements IModProxy {
	
	@Override
	public void construct() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.config);
		UModBlocks.construct();
		UModTileEntityTypes.construct();
	}
	
	@Override
	public void setup() {
	}
	
	@Override
	public void complete() {
		UModItemGroups.complete();
	}
	
}
