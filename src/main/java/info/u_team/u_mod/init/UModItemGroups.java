package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_team_core.itemgroup.UItemGroup;
import net.minecraft.item.ItemGroup;

public class UModItemGroups {
	
	public static final ItemGroup GROUP = new UItemGroup(UMod.MODID, "group", () -> UModBlocks.CRATE);
	
}
