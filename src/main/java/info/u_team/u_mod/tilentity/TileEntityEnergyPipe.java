package info.u_team.u_mod.tilentity;

import java.util.ArrayList;

import info.u_team.u_mod.api.ICable;
import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_mod.api.TunnelHandler;
import info.u_team.u_mod.block.EnergyPipeBlock;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityEnergyPipe extends UTileEntity implements ICable, ITickable {
	
	private int id = -1;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		id = compound.getInteger("tunnel");
		TunnelHandler.registerTunnel(id, pos, world);
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("tunnel", this.id);
	}
	
	@Override
	public void onLoad() {
		if (!this.world.isRemote && this.id == -1) {
			IBlockState state = this.world.getBlockState(pos).getActualState(world, pos);
			state.getBlock().onNeighborChange(world, pos, null);
			if (state.getValue(EnergyPipeBlock.UP)) {
				world.getBlockState(pos.up()).getBlock().onNeighborChange(world, pos.up(), pos);
			}
			if (state.getValue(EnergyPipeBlock.DOWN)) {
				world.getBlockState(pos.down()).getBlock().onNeighborChange(world, pos.down(), pos);
			}
			if (state.getValue(EnergyPipeBlock.EAST)) {
				world.getBlockState(pos.east()).getBlock().onNeighborChange(world, pos.east(), pos);
			}
			if (state.getValue(EnergyPipeBlock.WEST)) {
				world.getBlockState(pos.west()).getBlock().onNeighborChange(world, pos.west(), pos);
			}
			if (state.getValue(EnergyPipeBlock.NORTH)) {
				world.getBlockState(pos.north()).getBlock().onNeighborChange(world, pos.north(), pos);
			}
			if (state.getValue(EnergyPipeBlock.SOUTH)) {
				world.getBlockState(pos.south()).getBlock().onNeighborChange(world, pos.south(), pos);
			}
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
