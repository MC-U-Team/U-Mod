package info.u_team.u_mod;

import info.u_team.to_uteam_core.TestNetwork;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(UMod.MODID)
public class UMod {
	
	public static final String MODID = "umod";
	
	public UMod() {
		JarSignVerifier.checkSigned(MODID);
		TestNetwork.construct();
	}
}