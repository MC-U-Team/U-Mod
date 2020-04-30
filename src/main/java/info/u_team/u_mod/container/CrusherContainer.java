package info.u_team.u_mod.container;

import info.u_team.u_mod.container.basic.BasicMachineContainer;
import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.CrusherTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class CrusherContainer extends BasicMachineContainer<CrusherTileEntity> {
	
	// Client
	public CrusherContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(UModContainerTypes.CRUSHER, id, playerInventory, buffer);
	}
	
	// Server
	public CrusherContainer(int id, PlayerInventory playerInventory, CrusherTileEntity tileEntity) {
		super(UModContainerTypes.CRUSHER, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		super.init(server);
		appendInventory(tileEntity.getIngredientSlots(), 1, 1, 44, 50);
		appendOutputInventory(tileEntity.getOutputSlots(), 2, 3, 116, 41);
		appendPlayerInventory(playerInventory, 8, 92);
	}
	
}
