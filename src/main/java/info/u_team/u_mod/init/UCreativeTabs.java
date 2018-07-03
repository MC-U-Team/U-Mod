package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.creativetab.UCreativeTab;

public class UCreativeTabs {
	
	public static final UCreativeTab RESOURCES = new UCreativeTab(UConstants.MODID, "resources");
	public static final UCreativeTab MACHINE = new UCreativeTab(UConstants.MODID, "machine");
	
	public static void init() {
		MACHINE.setIcon(UBlocks.pulverizer);
		RESOURCES.setIcon(UBlocks.ore1);
	}
	
}
