package info.u_team.u_mod.container;

import info.u_team.u_mod.container.basic.BasicMachineContainer;
import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.ElectricFurnaceTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ElectricFurnaceContainer extends BasicMachineContainer<ElectricFurnaceTileEntity> {
	
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
		super.init(server);
		appendInventory(tileEntity.getIngredientSlots(), 1, 1, 44, 50);
		appendOutputInventory(tileEntity.getOutputSlots(), 2, 3, 116, 41);
		appendUpgradeInventory(tileEntity.getUpgradeSlots(), 1, 3, 116, 8);
		appendPlayerInventory(playerInventory, 8, 92);
	}
	
}
