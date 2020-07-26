package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.screen.*;
import info.u_team.u_team_core.util.registry.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = UMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class UModScreens {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		MainThreadWorker.run(() -> {
			ClientRegistry.registerScreen(UModContainerTypes.ELECTRIC_FURNACE, ElectricFurnaceScreen::new);
			ClientRegistry.registerScreen(UModContainerTypes.CRUSHER, CrusherScreen::new);
			ClientRegistry.registerScreen(UModContainerTypes.ORE_WASHER, OreWasherScreen::new);
		});
	}
}
