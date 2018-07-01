package info.u_team.u_mod.tilentity;

import info.u_team.u_mod.api.IEnergyStorageProvider;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.IEnergyStorage;

public class UTileEntityBattery extends UTileEntity implements ITickable {
	
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY;
	
	private IEnergyStorage energy;
	
	public UTileEntityBattery() {
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
		for (EnumFacing facing : EnumFacing.VALUES) {
			TileEntity entity = world.getTileEntity(pos.offset(facing));
			if (entity instanceof IEnergyStorageProvider) {
				IEnergyStorage storage = ((IEnergyStorageProvider) entity).getStorage();
				storage.receiveEnergy(2, false);
			}
		}
	}
	
}
