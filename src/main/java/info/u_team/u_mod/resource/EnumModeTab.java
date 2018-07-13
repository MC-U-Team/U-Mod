package info.u_team.u_mod.resource;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import info.u_team.u_mod.init.UBlocks;;

public enum EnumModeTab {
	
	NORMAL, ENERGY(UBlocks.energy_pipe);
	
	public final Block item;
	
	private EnumModeTab() {
		this.item = null;
	}
	
	private EnumModeTab(Block item) {
		this.item = item;
	}
}
