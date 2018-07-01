package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.BlockBattery;
import info.u_team.u_mod.block.BlockOre;
import info.u_team.u_mod.block.BlockPulveriser;
import info.u_team.u_mod.block.EnergyPipeBlock;
import info.u_team.u_mod.resource.EnumResources;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class UBlocks {
	
	public static final UBlock ore1 = new BlockOre("ore1", EnumResources.BLOCK1_LIST);
	public static final UBlock ore2 = new BlockOre("ore2", EnumResources.BLOCK2_LIST);
	
	public static final UBlock block1 = new BlockOre("block1", EnumResources.BLOCK1_LIST);
	public static final UBlock block2 = new BlockOre("block2", EnumResources.BLOCK2_LIST);
	
	public static final UBlock pulverizer = new BlockPulveriser("pulverizer");
	public static final UBlock battery = new BlockBattery("battery");
	public static final UBlock energy_pipe = new EnergyPipeBlock("energy_pipe");
	
	public static void init() {
		BlockRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, UBlocks.class));
	}
	
}
