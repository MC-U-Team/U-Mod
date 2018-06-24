package info.u_team.u_mod.items;

import info.u_team.u_mod.UConstants;
import net.minecraft.item.Item;

public class UItem extends Item {
	
	public UItem(String name) {
		this.setRegistryName(UConstants.MODID, name);
		this.setUnlocalizedName(UConstants.MODID + ":" + name);
	}
	
}
