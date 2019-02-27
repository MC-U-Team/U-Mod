package info.u_team.u_mod.proxy;

import info.u_team.u_mod.api.CapabilityIOMode;
import info.u_team.u_mod.api.TunnelHandler;
import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		UBlocks.preinit();
		UItems.preinit();
		UGuis.preinit();
		UBiomes.preinit();
		
		CommonRegistry.registerEventHandler(TunnelHandler.class);
		CapabilityIOMode.register();
	}
	
	public void init(FMLInitializationEvent event) {
		UCreativeTabs.init();
		UBiomes.init();
		UGeneration.init();
		UOreDirectory.init();
		URecipes.init();
	}

	public void postinit(FMLPostInitializationEvent event) {
		
	}
}
