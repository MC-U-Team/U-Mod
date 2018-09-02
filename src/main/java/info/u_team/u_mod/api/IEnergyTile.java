package info.u_team.u_mod.api;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyTile extends IEnergyStorage, INbtSerializable {
	
	int getTransfer();
	
	void setTransfer(int transfer);
	
	void setCapacity(int capacity);
	
}
