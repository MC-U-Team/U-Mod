package info.u_team.u_mod;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.creativetab.CreativeTabs;

public class UConstants {
	
	public static final String MODID = "umod";
	public static final String NAME = "U Mod";
	public static final String VERSION = "@VERSION@";
	
	public static final String COMMONPROXY = "info.u_team.u_mod.proxy.CommonProxy";
	public static final String CLIENTPROXY = "info.u_team.u_mod.proxy.ClientProxy";
	
	public static final UCreativeTab MACHINE = new UCreativeTab(MODID, "machine");
	
}
