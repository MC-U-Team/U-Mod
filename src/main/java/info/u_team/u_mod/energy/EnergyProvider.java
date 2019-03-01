package info.u_team.u_mod.energy;

import net.minecraft.util.ITickable;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyProvider implements IEnergyStorage, ITickable {
	
	protected int storage;
	protected int capacity;
	protected int transfer;
	
	protected int generation;
	
	public EnergyProvider() {
	}
	
	public void init(int storage, int capacity, int transfer, int generation) {
		this.storage = Math.min(storage, capacity);
		this.capacity = capacity;
		this.transfer = transfer;
		this.generation = generation;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void setStorage(int storage) {
		this.storage = storage;
	}
	
	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}
	
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getStorage() {
		return storage;
	}
	
	public int getTransfer() {
		return transfer;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - storage, maxReceive);
		if (!simulate)
			storage += energyReceived;
		return energyReceived;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(storage, Math.min(transfer, maxExtract));
		if (!simulate)
			storage -= energyExtracted;
		return energyExtracted;
	}
	
	@Override
	public int getEnergyStored() {
		return storage;
	}
	
	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}
	
	@Override
	public boolean canExtract() {
		return true;
	}
	
	@Override
	public boolean canReceive() {
		return false;
	}
	
	@Override
	public void tick() {
		receiveEnergy(generation, false);
	}
	
}
