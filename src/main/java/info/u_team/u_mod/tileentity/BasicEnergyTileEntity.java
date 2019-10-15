package info.u_team.u_mod.tileentity;

import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public class BasicEnergyTileEntity extends UTileEntity {
	
	protected final LazyOptional<BasicEnergyStorage> internalStorage;
	
	public BasicEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract) {
		this(type, capacity, maxReceive, maxExtract, 0);
	}
	
	public BasicEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, int currentEnergy) {
		super(type);
		internalStorage = LazyOptional.of(() -> new BasicEnergyStorage(capacity, maxReceive, maxExtract, currentEnergy));
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		internalStorage.ifPresent(handler -> compound.put("energy", handler.serializeNBT()));
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		internalStorage.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("energy")));
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
		if (capability == CapabilityEnergy.ENERGY && direction == null) { // Only expose capability internal and not to other sides.
			return internalStorage.cast();
		}
		return super.getCapability(capability);
	}
	
	@Override
	public void remove() {
		super.remove();
		internalStorage.invalidate();
	}
	
}
