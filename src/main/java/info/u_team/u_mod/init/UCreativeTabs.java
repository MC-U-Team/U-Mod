package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.resource.EnumResources1;
import info.u_team.u_team_core.creativetab.UCreativeTab;

public class UCreativeTabs {
	
	public static final UCreativeTab resources = new UCreativeTab(UConstants.MODID, "resources");
	public static final UCreativeTab machine = new UCreativeTab(UConstants.MODID, "machine");
	
	public static void init() {
		machine.setIcon(UBlocks.pulverizer);
		resources.setIcon(UBlocks.resource_ore1, EnumResources1.COPPER.getMetadata());
	}
	
}
