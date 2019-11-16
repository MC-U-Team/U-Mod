package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.loot.SetTileEntityNBT;
import net.minecraft.block.Block;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = UMod.MODID, bus = Bus.MOD)
public class UModLootTableRegistry {
	
	@SubscribeEvent
	public static void register(Register<Block> event) { // We use this event for registering because its fired in the right place
		LootFunctionManager.registerFunction(new SetTileEntityNBT.Serializer());
	}
	
}
