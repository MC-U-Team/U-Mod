package info.u_team.u_mod.container;

import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.ElectricFurnaceTileEntity;
import info.u_team.u_team_core.container.*;
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
	}
	
}
