package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.block.*;
import info.u_team.u_mod.block.basic.BasicMachineBlock;
import info.u_team.u_team_core.util.registry.BlockDeferredRegister;
import net.minecraftforge.eventbus.api.IEventBus;

public class UModBlocks {
	
	public static final BlockDeferredRegister BLOCKS = BlockDeferredRegister.create(UMod.MODID);
	
	public static final CableBlock CABLE = new CableBlock("cable_test");
	
	public static final EnergyStorageBlock ENERGY_STORAGE = new EnergyStorageBlock("energy_storage");
	
	public static final BasicMachineBlock ELECTRIC_FURNACE = new BasicMachineBlock("electric_furnace", () -> UModTileEntityTypes.ELECTRIC_FURNACE);
	public static final BasicMachineBlock CRUSHER = new BasicMachineBlock("crusher", () -> UModTileEntityTypes.CRUSHER);
	public static final BasicMachineBlock ORE_WASHER = new BasicMachineBlock("ore_washer", () -> UModTileEntityTypes.ORE_WASHER);
	
	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}
	
}
