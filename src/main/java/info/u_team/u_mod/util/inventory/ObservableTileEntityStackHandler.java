package info.u_team.u_mod.util.inventory;

import java.util.stream.IntStream;

import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import net.minecraft.tileentity.TileEntity;

public class ObservableTileEntityStackHandler extends TileEntityUItemStackHandler {
	
	public ObservableTileEntityStackHandler(int size, TileEntity tileEntity) {
		super(size, tileEntity);
	}
	
	@Override
	protected void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		
	}
	
	public boolean isEmpty() {
		return IntStream.range(0, getSlots()).allMatch(index -> getStackInSlot(index).isEmpty());
	}
	
}
