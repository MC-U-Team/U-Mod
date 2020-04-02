package info.u_team.u_mod.tileentity.basic;

import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.tileentity.UTickableTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BasicEnergyTileEntity extends UTickableTileEntity {
	
	protected final BasicEnergyStorage internalEnergyStorage;
	
	protected final LazyOptional<BasicEnergyStorage> internalEnergyStorageOptional;
	
	public BasicEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract) {
		this(type, capacity, maxReceive, maxExtract, 0);
	}
	
	public BasicEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, int currentEnergy) {
		super(type);
		internalEnergyStorage = new BasicEnergyStorage(capacity, maxReceive, maxExtract, currentEnergy);
		internalEnergyStorageOptional = LazyOptional.of(() -> internalEnergyStorage);
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		compound.put("energy", internalEnergyStorage.serializeNBT());
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		internalEnergyStorage.deserializeNBT(compound.getCompound("energy"));
	}
	
	// Invalidate lazy optional
	
	@Override
	public void remove() {
		super.remove();
		internalEnergyStorageOptional.invalidate();
	}
	
	// Getter
	
	public BasicEnergyStorage getInternalEnergyStorage() {
		return internalEnergyStorage;
	}
	
	public LazyOptional<BasicEnergyStorage> getInternalEnergyStorageOptional() {
		return internalEnergyStorageOptional;
	}
	
}
