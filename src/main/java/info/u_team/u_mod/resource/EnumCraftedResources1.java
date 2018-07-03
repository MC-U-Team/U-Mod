package info.u_team.u_mod.resource;

import info.u_team.u_team_core.api.IMetaType;

public enum EnumCraftedResources1 implements IMetaType {
	
	COALCHUNK(0, "coalchunk"),
	CARBON(1, "carbon");
	
	private int metadata;
	private String name;
	
	private EnumCraftedResources1(int metadata, String name) {
		this.metadata = metadata;
		this.name = name;
	}
	
	@Override
	public int getMetadata() {
		return metadata;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public static final EnumCraftedResources1[] VALUES = new EnumCraftedResources1[values().length];
	
	static {
		for (EnumCraftedResources1 entry : values()) {
			VALUES[entry.getMetadata()] = entry;
		}
	}
	
	public static EnumCraftedResources1 byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		
		return VALUES[meta];
	}
	
}
