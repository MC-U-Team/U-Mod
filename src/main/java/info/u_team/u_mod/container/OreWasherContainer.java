package info.u_team.u_mod.container;

import info.u_team.u_mod.container.basic.BasicMachineContainer;
import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.OreWasherTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class OreWasherContainer extends BasicMachineContainer<OreWasherTileEntity> {
	
	// Client
	public OreWasherContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(UModContainerTypes.ORE_WASHER, id, playerInventory, buffer);
	}
	
	// Server
	public OreWasherContainer(int id, PlayerInventory playerInventory, OreWasherTileEntity tileEntity) {
		super(UModContainerTypes.ORE_WASHER, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		super.init(server);
		appendFluidInventory(tileEntity.getFluidIngredientSlots(), 1, 1, 20, 20);
		appendInventory(tileEntity.getIngredientSlots(), 1, 1, 44, 50);
		appendOutputInventory(tileEntity.getOutputSlots(), 2, 3, 116, 41);
		appendUpgradeInventory(tileEntity.getUpgradeSlots(), 1, 3, 116, 8);
		appendPlayerInventory(playerInventory, 8, 92);
	}
	
}
