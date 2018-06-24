package info.u_team.u_mod;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class UBlocks {
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> register_event) {
		IForgeRegistry<Block> registery = register_event.getRegistry();
	}
	
}
