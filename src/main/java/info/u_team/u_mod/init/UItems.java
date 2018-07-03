package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.item.*;
import info.u_team.u_mod.resource.*;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.item.Item;

public class UItems {
	
	public static final UItem dust1 = new ItemDust("dust1", EnumResources1.VALUES);
	public static final UItem dust2 = new ItemDust("dust2", EnumResources2.VALUES);
	
	public static final UItem nugget1 = new ItemNugget("nugget1", EnumResources1.VALUES);
	public static final UItem nugget2 = new ItemNugget("nugget2", EnumResources2.VALUES);
	
	public static final UItem ingot1 = new ItemIngot("ingot1", EnumResources1.VALUES);
	public static final UItem ingot2 = new ItemIngot("ingot2", EnumResources2.VALUES);
	
	public static void init() {
		ItemRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Item.class, UItems.class));
	}
	
}
