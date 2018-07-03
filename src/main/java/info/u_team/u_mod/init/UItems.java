package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.item.*;
import info.u_team.u_mod.resource.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.item.Item;

public class UItems {
	
	// Resources
	public static final UItem resource_dust1 = new ItemDust("resource_dust1", EnumResources1.VALUES);
	public static final UItem resource_dust2 = new ItemDust("resource_dust2", EnumResources2.VALUES);
	
	public static final UItem resource_nugget1 = new ItemNugget("resource_nugget1", EnumResources1.VALUES);
	public static final UItem resource_nugget2 = new ItemNugget("resource_nugget2", EnumResources2.VALUES);
	
	public static final UItem resource_ingot1 = new ItemIngot("resource_ingot1", EnumResources1.VALUES);
	public static final UItem resource_ingot2 = new ItemIngot("resource_ingot2", EnumResources2.VALUES);
	
	public static final UItem alloy_nugget1 = new ItemNugget("alloy_nugget1", EnumAlloys1.VALUES);
	
	public static final UItem alloy_ingot1 = new ItemIngot("alloy_ingot1", EnumAlloys1.VALUES);
	
	public static void init() {
		ItemRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Item.class, UItems.class));
	}
	
}
