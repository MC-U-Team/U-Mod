package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.block.*;
import info.u_team.u_mod.block.basic.BasicMachineBlock;
import info.u_team.u_team_core.util.registry.*;
import net.minecraft.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;

public class UModBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(UMod.MODID);
	
	public static final BlockRegistryObject<CableBlock, BlockItem> CABLE = BLOCKS.register("cable_test", CableBlock::new);
	
	public static final BlockRegistryObject<EnergyStorageBlock, BlockItem> ENERGY_STORAGE = BLOCKS.register("energy_storage", EnergyStorageBlock::new);
	
	public static final BlockRegistryObject<BasicMachineBlock, BlockItem> ELECTRIC_FURNACE = BLOCKS.register("electric_furnace", () -> new BasicMachineBlock(UModTileEntityTypes.ELECTRIC_FURNACE));
	public static final BlockRegistryObject<BasicMachineBlock, BlockItem> CRUSHER = BLOCKS.register("crusher", () -> new BasicMachineBlock(UModTileEntityTypes.CRUSHER));
	public static final BlockRegistryObject<BasicMachineBlock, BlockItem> ORE_WASHER = BLOCKS.register("ore_washer", () -> new BasicMachineBlock(UModTileEntityTypes.ORE_WASHER));
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
