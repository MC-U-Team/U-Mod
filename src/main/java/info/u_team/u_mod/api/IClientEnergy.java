package info.u_team.u_mod.api;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IClientEnergy extends IEnergyStorageProvider{
	
	@SideOnly(Side.CLIENT)
	int getImpl();
	
}
