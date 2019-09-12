package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = UMod.MODID)
public class UModItems {
	
	@SubscribeEvent
	public static void register(Register<Item> event) {
	}
}
