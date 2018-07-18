package info.u_team.u_mod.tilentity;

import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityBattery extends UTileEntity implements ITickable, ICableExceptor {
	
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY;
	
	private IEnergyStorage energy;
	
	public TileEntityBattery() {
		energy = ENERGY.getDefaultInstance();
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		if (world.isRemote)
			return;
		energy.receiveEnergy(10, false);
	}
	
	@Override
	public IEnergyStorage getStorage() {
		return this.energy;
	}
	
	@Override
	public boolean takesEnergy() {
		return false;
	}
	
	@Override
	public boolean givesEnergy() {
		return true;
	}
	
	@Override
	public int rate() {
		return 8;
	}
	
}
