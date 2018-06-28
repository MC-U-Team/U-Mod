package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.Item;

public class UCreativeTabs {
	
	public static final UCreativeTab MACHINE = new UCreativeTab(UConstants.MODID, "machine");
	
	public static void init() {
		MACHINE.setIcon(UBlocks.pulverizer);
	}
	
}
