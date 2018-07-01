package info.u_team.u_mod.tilentity;

import info.u_team.u_mod.api.ICable;
import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.IEnergyStorage;

public class UEnergyPipeTile extends UTileEntity implements ICable, ITickable {
	
	private int id = -1;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isOutput() {
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity entity = world.getTileEntity(pos.offset(face));
			if (entity != null && entity instanceof ICableExceptor) {
				ICableExceptor exceptor = (ICableExceptor) entity;
				if (exceptor.takesEnergy()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isInput() {
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity entity = world.getTileEntity(pos.offset(face));
			if (entity != null && entity instanceof ICableExceptor) {
				ICableExceptor exceptor = (ICableExceptor) entity;
				if (exceptor.givesEnergy()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void setID(int id) {
		this.id = id;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public int rate() {
		return 3;
	}

	@Override
	public void update() {
	}
	
}
