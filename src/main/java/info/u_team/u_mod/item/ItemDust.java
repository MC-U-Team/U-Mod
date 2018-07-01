package info.u_team.u_mod.item;

import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.item.UItemMetaData;
import net.minecraft.util.NonNullList;

public class ItemDust extends UItemMetaData {
	
	public ItemDust(String name, NonNullList<IUMetaType> list) {
		super(name, UCreativeTabs.RESOURCES, list);
	}
	
}
