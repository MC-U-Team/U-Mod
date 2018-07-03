package info.u_team.u_mod.item;

import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.item.UItemMetaData;

public class ItemDust extends UItemMetaData {
	
	public ItemDust(String name, IMetaType[] list) {
		super(name, UCreativeTabs.RESOURCES, list);
	}
	
}
