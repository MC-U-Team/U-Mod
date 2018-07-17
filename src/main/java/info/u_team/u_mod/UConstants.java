package info.u_team.u_mod;

import org.apache.logging.log4j.*;

public class UConstants {
	
	public static final String MODID = "umod";
	public static final String NAME = "UMod";
	public static final String VERSION = "${version}";
	public static final String MCVERSION = "${mcversion}";
	public static final String DEPENDENCIES = "required-after:uteamcore@[2.0.0.75-SNAPSHOT,);required:forge@[14.23.4.2705,)";
	public static final String UPDATEURL = "https://api.u-team.info/update/umod.json";
	
	public static final String COMMONPROXY = "info.u_team.u_mod.proxy.CommonProxy";
	public static final String CLIENTPROXY = "info.u_team.u_mod.proxy.ClientProxy";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	private UConstants() {
	}
	
}
