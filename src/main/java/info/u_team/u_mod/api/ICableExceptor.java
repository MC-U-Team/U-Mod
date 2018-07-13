package info.u_team.u_mod.api;

public interface ICableExceptor extends IEnergyStorageProvider {
	
	boolean takesEnergy();
	
	boolean givesEnergy();
	
	int rate();
}
