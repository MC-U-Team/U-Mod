package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.creativetab.UCreativeTab;

public class UCreativeTabs {
	
	public static final UCreativeTab MACHINE = new UCreativeTab(UConstants.MODID, "machine");
	public static final UCreativeTab MATERIAL = new UCreativeTab(UConstants.MODID, "material");
	
	public static void init() {
		MACHINE.setIcon(UBlocks.pulverizer); // TODO pulverizer is not displayed, why?? Maybe model error
	}
	
}
