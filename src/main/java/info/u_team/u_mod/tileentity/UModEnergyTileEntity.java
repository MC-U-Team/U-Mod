package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.energy.EnergySystem;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class UModEnergyTileEntity extends UTileEntity implements ITickableTileEntity{

	private final EnergyStorage storage; // Actual capability energy storage
	private final int pull; // the energy to pull
	
	private int depth = 20; // Default cable search depth
	
	public UModEnergyTileEntity(TileEntityType<?> type, int capacity, int pull, int send) {
		this(type, capacity, pull, send, 0);
	}
	
	public UModEnergyTileEntity(TileEntityType<?> type, int capacity, int pull, int send, int cap) {
		super(type);
		this.storage = new EnergyStorage(capacity, pull, send, cap);
		this.pull = pull;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		// TODO Sided implementation
		if(cap.equals(CapabilityEnergy.ENERGY))
			return (LazyOptional<T>) LazyOptional.of(() -> storage);
		return super.getCapability(cap, side);
	}	
	
	@Override
	public void tick() {
		if(storage.canReceive())
			storage.receiveEnergy(EnergySystem.pullEnergyFromNetwork(getWorld(), getPos(), this.pull, this.depth), false);
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public EnergyStorage getEnergyStorage() {
		return this.storage;
	}

}
