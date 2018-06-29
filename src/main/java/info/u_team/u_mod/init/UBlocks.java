package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.*;
import info.u_team.u_team_core.block.UBlock;
import info.u_team.u_team_core.registry.BlockRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.block.Block;

public class UBlocks {
	
	public static final UBlock pulverizer = new UPulverizerBlock("pulverizer");
	public static final UBlock battery = new UBatteryBlock("battery");
	public static final UBlock energy_pipe = new UEnergyPipeBlock("energy_pipe");
	
	public static void init() {
		BlockRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Block.class, UBlocks.class));
	}
	
}
