package info.u_team.u_mod;

import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(UMod.MODID)
public class UMod {
	
	public static final String MODID = "umod";
	
	public UMod() {
		JarSignVerifier.checkSigned(MODID);
		register();
	}
	
	private void register() {
		BusRegister.registerMod(UModBlocks::register);
		BusRegister.registerMod(UModContainerTypes::register);
		BusRegister.registerMod(UModItems::register);
		BusRegister.registerMod(UModRecipeSerializers::register);
	}
}