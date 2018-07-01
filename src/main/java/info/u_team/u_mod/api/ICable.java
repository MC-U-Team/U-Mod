package info.u_team.u_mod.api;

import net.minecraft.util.math.BlockPos;

public interface ICable {
	
	boolean isOutput();
	
	boolean isInput();
	
	void setID(int id);
	
	int getID();
	
	int rate();
	
	BlockPos getPos();
}
