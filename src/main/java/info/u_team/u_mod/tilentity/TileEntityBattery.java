package info.u_team.u_mod.tilentity;

import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_mod.energy.EnergyStorage;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.*;

public class TileEntityBattery extends UTileEntity implements ITickable, ICableExceptor {
	
	protected final EnergyStorage energy;
	
	public TileEntityBattery() {
		energy = new EnergyStorage(100000, 10000, 10000);
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		energy.readNBT(compound);
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		energy.writeNBT(compound);
	}
	
	@Override
	public void update() {
		if (world.isRemote)
			return;
		energy.receiveEnergy(5000, false);
	}
	
	@Override
	public IEnergyStorage getStorage() {
		return this.energy;
	}
	
	@Override
	public boolean takesEnergy(EnumFacing face) {
		return true;
	}
	
	@Override
	public boolean givesEnergy(EnumFacing face) {
		return true;
	}
	
	@Override
	public int rate() {
		return 1000;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energy);
		}
		return super.getCapability(capability, facing);
	}
	
}
