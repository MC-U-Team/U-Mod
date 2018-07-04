package info.u_team.u_mod.proxy;

import info.u_team.u_mod.handler.UClientEventHandler;
import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		CommonRegistry.registerEventHandler(new UClientEventHandler());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		UColors.init();
		UParticles.init();
	}
	
	@Override
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
}
