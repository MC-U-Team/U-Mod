package info.u_team.u_mod.energy;

import info.u_team.u_mod.api.INbtSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyConsumer implements IEnergyStorage, INbtSerializable {
	
	protected int energy;
	protected int capacity;
	protected int transfer;
	
	public EnergyConsumer(int capacity, int transfer) {
		this.capacity = capacity;
		this.transfer = transfer;
		this.energy = 0;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(transfer, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(transfer, maxExtract));
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
		return false;
	}
	
	@Override
	public boolean canReceive() {
		return true;
	}
	
	public int getTransfer() {
		return transfer;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		if (compound.hasKey("energy_energy")) {
			energy = compound.getInteger("energy_energy");
		}
		if (compound.hasKey("energy_capacity")) {
			capacity = compound.getInteger("energy_capacity");
		}
		if (compound.hasKey("energy_transfer")) {
			transfer = compound.getInteger("energy_transfer");
		}
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("energy_energy", energy);
		compound.setInteger("energy_capacity", capacity);
		compound.setInteger("energy_transfer", transfer);
	}
}