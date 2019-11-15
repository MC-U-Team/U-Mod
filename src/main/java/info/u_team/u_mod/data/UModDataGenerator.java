package info.u_team.u_mod.data;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.data.provider.*;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = UMod.MODID, bus = Bus.MOD)
public class UModDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(UMod.MODID, event);
		if (event.includeServer()) {
		}
		if (event.includeClient()) {
			data.addProvider(UModBlockStatesProvider::new);
			data.addProvider(UModItemModelsProvider::new);
		}
	}
}
