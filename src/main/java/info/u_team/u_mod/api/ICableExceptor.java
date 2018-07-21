package info.u_team.u_mod.api;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface ICableExceptor extends IEnergyStorageProvider {
	
	boolean takesEnergy();
	
	boolean givesEnergy();
	
	int rate();
	
	default boolean canConnectTo(EnumFacing face, BlockPos pos, IBlockAccess world) {
		return true;
	}
	
}
