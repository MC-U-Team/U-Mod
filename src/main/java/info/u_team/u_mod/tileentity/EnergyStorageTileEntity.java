package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyStorageTileEntity extends UTileEntity{

	public EnergyStorageTileEntity() {
		super(UModTileEntityTypes.ENERGYSTORAGE);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap.equals(CapabilityEnergy.ENERGY)) {
			return LazyOptional.of(() -> cap.getDefaultInstance()); 
		}
		return super.getCapability(cap, side);
	}

}
