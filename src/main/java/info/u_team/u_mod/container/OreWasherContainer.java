package info.u_team.u_mod.container;

import info.u_team.u_mod.container.basic.*;
import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.OreWasherTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class OreWasherContainer extends BasicMachineContainer<OreWasherTileEntity> {
	
	// Client
	public OreWasherContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(UModContainerTypes.ORE_WASHER.get(), id, playerInventory, buffer);
	}
	
	// Server
	public OreWasherContainer(int id, PlayerInventory playerInventory, OreWasherTileEntity tileEntity) {
		super(UModContainerTypes.ORE_WASHER.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		super.init(server);
		appendInventory(tileEntity.getIngredientSlots(), 1, 1, 44, 41);
		appendOutputInventory(tileEntity.getOutputSlots(), 1, 3, 116, 41);
		appendOutputInventory(tileEntity.getOutputSlots(), 1, 2, 116, 59); // TODO wrong index (must be fixed in uteamcore though)
		appendUpgradeInventory(tileEntity.getUpgradeSlots(), 1, 3, 116, 8);
		appendPlayerInventory(playerInventory, 8, 92);
		appendFluidInventory(tileEntity.getFluidIngredientSlots(), 1, 1, 44, 59);
		appendFluidInventory(tileEntity.getFluidOutputSlots(), BasicFluidOutputSlot::new, 1, 1, 152, 59);
	}
	
}
