package info.u_team.u_mod.container;

import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.ElectricFurnaceTileEntity;
import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.container.UTileEntityContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ElectricFurnaceContainer extends UTileEntityContainer<ElectricFurnaceTileEntity> {
	
	// Client
	public ElectricFurnaceContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(UModContainerTypes.ELECTRIC_FURNACE, id, playerInventory, buffer);
	}
	
	// Server
	public ElectricFurnaceContainer(int id, PlayerInventory playerInventory, ElectricFurnaceTileEntity tileEntity) {
		super(UModContainerTypes.ELECTRIC_FURNACE, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		addServerToClientTracker(BufferReferenceHolder.create(() -> {
			final PacketBuffer buffer = new PacketBuffer(Unpooled.directBuffer());
			tileEntity.getInternalStorage().ifPresent(storage -> buffer.writeInt(storage.getEnergyStored()));
			return buffer;
		}, buffer -> {
			tileEntity.getInternalStorage().ifPresent(storage -> storage.setEnergy(buffer.readInt()));
		}));
		
		addServerToClientTracker(BufferReferenceHolder.create(() -> {
			final PacketBuffer buffer = new PacketBuffer(Unpooled.directBuffer());
			buffer.writeFloat(Math.min(Math.max(tileEntity.getProgress() / tileEntity.getMaxProgress(), 1), 0));
			return buffer;
		}, buffer -> {
			tileEntity.setProgressPercentage(buffer.readFloat());
		}));
	}
	
}
