package info.u_team.u_mod.energy;

import info.u_team.u_mod.api.INbtSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorage implements IEnergyStorage, INbtSerializable {
	
	protected int energy;
	protected int capacity;
	protected int transferIn, transferOut;
	
	public EnergyStorage(int capacity, int transferIn, int transferOut) {
		this.capacity = capacity;
		this.transferIn = transferIn;
		this.transferOut = transferOut;
		this.energy = 0;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive()) {
			return 0;
		}
		int energyReceived = Math.min(capacity - energy, Math.min(transferIn, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!canExtract()) {
			return 0;
		}
		int energyExtracted = Math.min(energy, Math.min(transferOut, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}
	
	@Override
	public int getEnergyStored() {
		return energy;
	}
	
	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}
	
	@Override
	public boolean canExtract() {
		return transferOut > 0;
	}
	
	@Override
	public boolean canReceive() {
		return transferIn > 0;
	}
	
	public int getTransferIn() {
		return transferIn;
	}
	
	public int getTransferOut() {
		return transferIn;
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void setTransferIn(int transferIn) {
		this.transferIn = transferIn;
	}
	
	public void setTransferOut(int transferOut) {
		this.transferOut = transferOut;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		if (compound.hasKey("energy_energy")) {
			energy = compound.getInteger("energy_energy");
		}
		if (compound.hasKey("energy_capacity")) {
			capacity = compound.getInteger("energy_capacity");
		}
		if (compound.hasKey("energy_transfer_in")) {
			transferIn = compound.getInteger("energy_transfer_in");
		}
		if (compound.hasKey("energy_transfer_out")) {
			transferOut = compound.getInteger("energy_transfer_out");
		}
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("energy_energy", energy);
		compound.setInteger("energy_capacity", capacity);
		compound.setInteger("energy_transfer_in", transferIn);
		compound.setInteger("energy_transfer_out", transferOut);
	}
}