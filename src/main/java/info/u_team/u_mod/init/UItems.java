package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.item.ItemDust;
import info.u_team.u_mod.resources.EnumResources;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.item.Item;

public class UItems {
	
	public static final UItem dust = new ItemDust("dust", EnumResources.createItemList());
	public static final UItem ingot = new ItemDust("ingot", EnumResources.createItemList());
	
	public static void init() {
		ItemRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Item.class, UItems.class));
	}
	
}
