package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_mod.tileentity.basic.BasicEnergyTileEntity;

public class EnergyStorageTileEntity extends BasicEnergyTileEntity {
	
	public EnergyStorageTileEntity() {
		super(UModTileEntityTypes.ENERGY_STORAGE.get(), 1000, 10, 10, 1000);
	}
}
