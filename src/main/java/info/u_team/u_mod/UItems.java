package info.u_team.u_mod;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class UItems {
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> register_event) {
		IForgeRegistry<Item> registery = register_event.getRegistry();
	}
	
}
