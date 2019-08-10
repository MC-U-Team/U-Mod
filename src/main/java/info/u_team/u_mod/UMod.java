package info.u_team.u_mod;

import info.u_team.u_mod.proxy.*;
import info.u_team.u_team_core.api.IModProxy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UMod.MODID)
public class UMod {
	
	public static final String MODID = "umod";
	
	private static final IModProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public UMod() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		PROXY.construct();
	}
	
	@SubscribeEvent
	public void setup(FMLCommonSetupEvent event) {
		PROXY.setup();
	}
	
	@SubscribeEvent
	public void ready(FMLLoadCompleteEvent event) {
		PROXY.complete();
	}
}
