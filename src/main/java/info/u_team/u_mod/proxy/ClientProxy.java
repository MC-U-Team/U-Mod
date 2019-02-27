package info.u_team.u_mod.proxy;

import info.u_team.u_mod.handler.event.EventHandlerConfigChange;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.model.UEnergyPipeModel;
import info.u_team.u_mod.model.UModelLoader;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public void preinit(FMLPreInitializationEvent event) {
		ModelLoaderRegistry.registerLoader(new UModelLoader());
		UModelLoader.register("energy_pipe", UEnergyPipeModel.class);
		
		super.preinit(event);
		CommonRegistry.registerEventHandler(new EventHandlerConfigChange());
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
