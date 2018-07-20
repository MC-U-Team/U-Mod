package info.u_team.u_mod.tilentity;

import java.util.ArrayList;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.block.BlockEnergyPipe;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class TileEntityEnergyPipe extends UTileEntity implements ICable, ITickable {
	
	private int id = -1;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		id = compound.getInteger("tunnel");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("tunnel", this.id);
	}
	
	@Override
	public void onLoad() {
		if (this.id > -1) {
			TunnelHandler.registerTunnel(id, pos, world);
		}
		if (!this.world.isRemote && this.id == -1) {
			IBlockState state = this.world.getBlockState(pos).getActualState(world, pos);
			TunnelHandler.onStateChange(state, world, pos);
		}
	}
	
	@Override
	public EnumFacing[] isOutput() {
		ArrayList<EnumFacing> facings = new ArrayList<>();
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity entity = world.getTileEntity(pos.offset(face));
			if (entity != null && entity instanceof ICableExceptor) {
				ICableExceptor exceptor = (ICableExceptor) entity;
				if (exceptor.takesEnergy()) {
					facings.add(face);
				}
			}
		}
		return facings.toArray(new EnumFacing[facings.size()]);
	}
	
	@Override
	public EnumFacing[] isInput() {
		ArrayList<EnumFacing> facings = new ArrayList<>();
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity entity = world.getTileEntity(pos.offset(face));
			if (entity != null && entity instanceof ICableExceptor) {
				ICableExceptor exceptor = (ICableExceptor) entity;
				if (exceptor.givesEnergy()) {
					facings.add(face);
				}
			}
		}
		return facings.toArray(new EnumFacing[facings.size()]);
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
