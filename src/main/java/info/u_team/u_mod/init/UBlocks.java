package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.*;
import info.u_team.u_team_core.block.*;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class UBlocks {
	
	public static final UBlock ore = new UBlockMetaData("ore", Material.ROCK, UCreativeTabs.MATERIAL, NonNullListUtil.createMeta("alluminum", "cadmium", "chromium", "cobalt", "copper", "lead", "molybdenum", "nickel", "platinum", "silver", "tantalum", "tin", "tungsten", "zinc"));
	
	public static final UBlock pulverizer = new UBlockPulveriser("pulverizer");
	public static final UBlock battery = new UBlockBattery("battery");
	public static final UBlock energy_pipe = new UEnergyPipeBlock("energy_pipe");
	
	public static void init() {
		BlockRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, UBlocks.class));
	}
	
}
