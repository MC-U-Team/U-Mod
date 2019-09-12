package info.u_team.u_mod.container;

import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.CrateTileEntity;
import info.u_team.u_mod.type.Crate;
import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.CapabilityItemHandler;

public class CrateContainer extends UTileEntityContainer<CrateTileEntity> {
	
	public CrateContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(UModContainerTypes.CRATE, id, playerInventory, buffer);
	}
	
	public CrateContainer(int id, PlayerInventory playerInventory, CrateTileEntity tileEntity) {
		super(UModContainerTypes.CRATE, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		final Crate crate = tileEntity.getCrate();
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> appendInventory(handler, crate.getSlotHeight(), crate.getSlotWidth(), crate.getSlotX(), crate.getSlotY()));
		appendPlayerInventory(playerInventory, crate.getSlotPlayerX(), crate.getSlotPlayerY());
	}
	
}
