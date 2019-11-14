package info.u_team.u_mod.tileentity;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BasicEnergyTileEntity extends BasicTickableTileEntity implements IInitSyncedTileEntity {
	
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
	public void remove() {
		super.remove();
		internalStorage.invalidate();
	}
	
	// Inital send when container is opened
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		if (internalStorage.isPresent()) {
			internalStorage.ifPresent(storage -> buffer.writeInt(storage.getEnergy()));
		} else {
			buffer.writeInt(0); // Write data always, because else the reader might corrupt
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		internalStorage.ifPresent(storage -> storage.setEnergy(buffer.readInt()));
	}
	
	// Getter
	public LazyOptional<BasicEnergyStorage> getInternalStorage() {
		return internalStorage;
	}
	
}
