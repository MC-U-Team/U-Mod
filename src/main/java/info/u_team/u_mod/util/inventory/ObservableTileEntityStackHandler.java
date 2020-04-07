package info.u_team.u_mod.util.inventory;

import net.minecraft.tileentity.TileEntity;

public class ObservableTileEntityStackHandler extends ObservableStackHandler {
	
	protected final TileEntity tileEntity;
	
	public ObservableTileEntityStackHandler(int size, TileEntity tileEntity) {
		super(size);
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void itemChanged(int slot) {
		tileEntity.markDirty();
		super.itemChanged(slot);
		if (!tileEntity.getWorld().isRemote()) {
			System.out.println(tileEntity + " -> " + slot + " changed XOXOX");
		}
	}
	
}
