package info.u_team.u_mod.resource;

import java.util.*;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.util.NonNullListCustom;
import net.minecraft.util.NonNullList;

public enum EnumCraftedResources {
	
	COALCHUNK("coalchunk"),
	CARBON("carbon");
	
	private String name;
	
	private EnumCraftedResources(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static NonNullList<IUMetaType> createItemList() {
		List<IUMetaType> list = new ArrayList<>();
		for (EnumCraftedResources entry : values()) {
			list.add(new IUMetaType() {
				
				@Override
				public String getName() {
					return entry.getName();
				}
			});
		}
		return new NonNullListCustom<>(list, null);
	}
	
}
