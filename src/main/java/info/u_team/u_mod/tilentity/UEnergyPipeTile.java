package info.u_team.u_mod.tilentity;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class UEnergyPipeTile extends UTileEntity implements IEnergyStorageProvider{

	@Override
	public void readNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IEnergyStorage getStorage() {
		return null;
	}
	
}
