package info.u_team.u_mod.tileentity;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class UModEnergyTileEntity extends UTileEntity {

	private final EnergyStorage storage; // Actual capability energy storage
		
	public UModEnergyTileEntity(TileEntityType<?> type, int capacity, int pull, int send) {
		this(type, capacity, pull, send, 0);
	}
	
	public UModEnergyTileEntity(TileEntityType<?> type, int capacity, int pull, int send, int cap) {
		super(type);
		this.storage = new EnergyStorage(capacity, pull, send, cap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		// TODO Sided implementation
		if(cap.equals(CapabilityEnergy.ENERGY))
			return (LazyOptional<T>) LazyOptional.of(() -> storage);
		return super.getCapability(cap, side);
	}	
		
	public EnergyStorage getEnergyStorage() {
		return this.storage;
	}

}
