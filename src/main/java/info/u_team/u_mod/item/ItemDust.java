package info.u_team.u_mod.item;

import java.util.List;

import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.item.UItemMetaData;

public class ItemDust extends UItemMetaData {
	
	public ItemDust(String name, List<IUMetaType> list) {
		super(name, UCreativeTabs.RESOURCES, list);
	}
	
}
