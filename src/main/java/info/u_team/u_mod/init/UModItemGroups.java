package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_team_core.itemgroup.UItemGroup;
import net.minecraft.item.ItemGroup;

public class UModItemGroups {
	
	public static final ItemGroup machines = new UItemGroup(UMod.modid, "machines");
	public static final ItemGroup resources = new UItemGroup(UMod.modid, "resources");
	
	public static void complete() {
		// group.setIcon(Items.item);
	}
	
}
