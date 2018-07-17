package info.u_team.u_mod.resource;

import info.u_team.u_mod.init.UBlocks;
import net.minecraft.block.Block;;

public enum EnumModeTab {
	
	NORMAL,
	ENERGY(UBlocks.battery);
	
	public final Block item;
	
	private EnumModeTab() {
		this.item = null;
	}
	
	private EnumModeTab(Block item) {
		this.item = item;
	}
}
