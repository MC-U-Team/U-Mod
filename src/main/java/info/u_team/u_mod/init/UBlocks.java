package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.*;
import info.u_team.u_mod.resource.*;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class UBlocks {
	
	// Resources
	public static final UBlock resource_ore1 = new BlockOre("resource_ore1", EnumResources1.VALUES);
	public static final UBlock resource_ore2 = new BlockOre("resource_ore2", EnumResources2.VALUES);
	
	public static final UBlock resource_block1 = new BlockBlock("resource_block1", EnumResources1.VALUES);
	public static final UBlock resource_block2 = new BlockBlock("resource_block2", EnumResources2.VALUES);
	
	public static final UBlock alloy_block1 = new BlockBlock("alloy_block1", EnumAlloys1.VALUES);
	
	// Machines
	public static final UBlock pulverizer = new BlockPulveriser("pulverizer");
	public static final UBlock battery = new BlockBattery("battery");
	public static final UBlock energy_pipe = new BlockEnergyPipe("energy_pipe");
	public static final UBlock furnace = new BlockFurnace("furnace");
	public static final UBlock alloy_furnace = new BlockAlloyFurnace("alloy_furnace");
	public static final UBlock press = new BlockPress("press");
	public static final UBlock enricher = new BlockEnricher("enricher");

	public static void preinit() {
		BlockRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, UBlocks.class));
	}
	
}
