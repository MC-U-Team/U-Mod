package info.u_team.u_mod.api;

import net.minecraftforge.fml.relauncher.*;

public interface IClientProgress {
	
	@SideOnly(Side.CLIENT)
	int getImplProgress();
	
}
