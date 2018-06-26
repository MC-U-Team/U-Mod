package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.item.Item;

public class UItems {
	
	public static void init() {
		ItemRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Item.class, UItems.class));
	}
	
}
