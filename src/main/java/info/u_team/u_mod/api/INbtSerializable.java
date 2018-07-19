package info.u_team.u_mod.api;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtSerializable {
	
	public void readNBT(NBTTagCompound compound);
	
	public void writeNBT(NBTTagCompound compound);
	
}
