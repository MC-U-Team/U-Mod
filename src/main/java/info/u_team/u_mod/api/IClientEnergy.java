package info.u_team.u_mod.api;

import net.minecraftforge.fml.relauncher.*;

public interface IClientEnergy extends IEnergyStorageProvider{
	
	@SideOnly(Side.CLIENT)
	int getImpl();
	
}
