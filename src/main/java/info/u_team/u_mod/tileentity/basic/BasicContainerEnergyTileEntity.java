package info.u_team.u_mod.tileentity.basic;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.*;

public abstract class BasicContainerEnergyTileEntity extends BasicEnergyTileEntity implements IInitSyncedTileEntity {
	
	public BasicContainerEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract) {
		this(type, capacity, maxReceive, maxExtract, 10000); // Debug TODO
	}
	
	public BasicContainerEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, int currentEnergy) {
		super(type, capacity, maxReceive, maxExtract, currentEnergy);
	}
	
	// Inital send when container is opened
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeInt(internalEnergyStorage.getEnergy());
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		internalEnergyStorage.setEnergy(buffer.readInt());
	}
	
}
