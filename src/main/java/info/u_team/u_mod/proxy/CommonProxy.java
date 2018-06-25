package info.u_team.u_mod.proxy;

import info.u_team.u_mod.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(UConstants.MODID, new UGuihandler());

		MinecraftForge.EVENT_BUS.register(UBlocks.class);
		MinecraftForge.EVENT_BUS.register(UItems.class);
		MinecraftForge.EVENT_BUS.register(UModels.class);

		UConstants.MACHINE.setIcon(UBlocks.pulverizer);
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
