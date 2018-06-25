package info.u_team.u_mod;

import info.u_team.u_mod.blocks.UPulverizerBlock;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class UBlocks {
	
	public static UBlock pulverizer = new UPulverizerBlock("pulverizer");
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> register_event) {
		IForgeRegistry<Block> registery = register_event.getRegistry();
		registery.register(pulverizer);
	}
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		registry.register(new UItemBlock(pulverizer));
	}
}
