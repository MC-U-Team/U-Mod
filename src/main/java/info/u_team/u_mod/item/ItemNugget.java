package info.u_team.u_mod.item;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.item.UItemMetaData;
import net.minecraft.util.ResourceLocation;

public class ItemNugget extends UItemMetaData {
	
	public ItemNugget(String name, IMetaType[] list) {
		super(name, UCreativeTabs.RESOURCES, list);
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < getList().length; i++) {
			setModel(this, i, new ResourceLocation(UConstants.MODID, "nugget"));
		}
	}
}
