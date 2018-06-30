package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.UBlockBattery;
import info.u_team.u_mod.block.UBlockOre;
import info.u_team.u_mod.block.UBlockPulveriser;
import info.u_team.u_mod.block.UEnergyPipeBlock;
import info.u_team.u_mod.resources.EnumOres;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class UBlocks {
	
	public static final UBlock ore1 = new UBlockOre("ore1", EnumOres.createList(EnumOres.Type.ORE1));
	public static final UBlock ore2 = new UBlockOre("ore2", EnumOres.createList(EnumOres.Type.ORE2));
	
	public static final UBlock pulverizer = new UBlockPulveriser("pulverizer");
	public static final UBlock battery = new UBlockBattery("battery");
	public static final UBlock energy_pipe = new UEnergyPipeBlock("energy_pipe");
	
	public static void init() {
		BlockRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, UBlocks.class));
	}
	
}
