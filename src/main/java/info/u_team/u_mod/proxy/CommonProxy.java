package info.u_team.u_mod.proxy;

import info.u_team.u_mod.init.*;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		
		UBlocks.init();
		UItems.init();
		
		UCreativeTabs.init();
		
		UGuis.init();
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
