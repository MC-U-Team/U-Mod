package info.u_team.u_mod.tileentity;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.tileentity.*;

public abstract class BasicTickableTileEntity extends UTileEntity implements ITickableTileEntity {
	
	public BasicTickableTileEntity(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public final void tick() {
		if (world.isRemote()) {
			tickClient();
		} else {
			tickServer();
		}
	}
	
	protected void tickServer() {
	}
	
	protected void tickClient() {
	}
	
}
