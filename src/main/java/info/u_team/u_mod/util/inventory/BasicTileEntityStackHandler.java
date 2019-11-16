package info.u_team.u_mod.util.inventory;

import java.util.stream.IntStream;

import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import net.minecraft.tileentity.TileEntity;

// TODO Do this in uteamcore
public class BasicTileEntityStackHandler extends TileEntityUItemStackHandler {
	
	public BasicTileEntityStackHandler(int size, TileEntity tileEntity) {
		super(size, tileEntity);
	}
	
	public boolean isEmpty() {
		return IntStream.range(0, getSlots()).allMatch(index -> getStackInSlot(index).isEmpty());
	}
	
}
