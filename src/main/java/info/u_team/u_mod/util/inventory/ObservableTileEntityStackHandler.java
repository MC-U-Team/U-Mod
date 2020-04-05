package info.u_team.u_mod.util.inventory;

import net.minecraft.tileentity.TileEntity;

public class ObservableTileEntityStackHandler extends ObservableStackHandler {
	
	protected final TileEntity tileEntity;
	
	public ObservableTileEntityStackHandler(int size, TileEntity tileEntity) {
		super(size);
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void onContentsChanged(int slot) {
		tileEntity.markDirty();
		super.onContentsChanged(slot);
	}
	
}
