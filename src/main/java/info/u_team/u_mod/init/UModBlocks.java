package info.u_team.u_mod.init;

import java.util.List;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.block.*;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = UMod.MODID)
public class UModBlocks {
	
	public static final CableBlock CABLE = new CableBlock("cable_test");
	
	public static final EnergyStorageBlock ENERGY_STORAGE = new EnergyStorageBlock("energy_storage");
	
	public static final EnergyFurnceBlock ENERGY_FURNACE = new EnergyFurnceBlock("energy_furnace");
	
	@SubscribeEvent
	public static void register(Register<Block> event) {
		entries = BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(UMod.MODID, Block.class);
		entries.forEach(event.getRegistry()::register);
	}
	
	@SubscribeEvent
	public static void registerBlockItem(Register<Item> event) {
		BaseRegistryUtil.getBlockItems(entries).forEach(event.getRegistry()::register);
		entries = null; // Dereference list as it is no longer needed
	}
	
	// Just a cache for the block item registry for performance
	private static List<Block> entries;
	
}
