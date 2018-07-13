package info.u_team.u_mod.api;

import info.u_team.u_team_core.api.IMetaType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public interface IResource extends IMetaType, IColored {
	
	public IBlockState getBlockState(Block block);
	
	public int getEnumCount();
	
}
