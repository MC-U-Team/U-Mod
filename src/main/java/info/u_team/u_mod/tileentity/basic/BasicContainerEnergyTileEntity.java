package info.u_team.u_mod.tileentity.basic;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.*;

public abstract class BasicContainerEnergyTileEntity extends BasicEnergyTileEntity implements IInitSyncedTileEntity {
	
	public BasicContainerEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract) {
		this(type, capacity, maxReceive, maxExtract, 0);
	}
	
	public BasicContainerEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, int currentEnergy) {
		super(type, capacity, maxReceive, maxExtract, currentEnergy);
	}
	
	// Inital send when container is opened
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		if (internalEnergyStorage.isPresent()) {
			internalEnergyStorage.ifPresent(storage -> buffer.writeInt(storage.getEnergy()));
		} else {
			buffer.writeInt(0); // Write data always, because else the reader might corrupt
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		internalEnergyStorage.ifPresent(storage -> storage.setEnergy(buffer.readInt()));
	}
	
}
