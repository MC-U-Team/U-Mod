package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT, modid = UMod.MODID)
public class UModGuis {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
	}
	
}
