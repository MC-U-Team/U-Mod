package info.u_team.u_mod.resource;

import info.u_team.u_mod.api.IColored;
import info.u_team.u_team_core.api.IMetaType;

public enum EnumCraftedResources1 implements IMetaType, IColored {
	
	COALCHUNK(0, "coalchunk", 0xF9F9F9),
	CARBON(1, "carbon", 0xF9F9F9);
	
	private int metadata;
	private String name;
	private int color;
	
	private EnumCraftedResources1(int metadata, String name, int color) {
		this.metadata = metadata;
		this.name = name;
		this.color = color;
	}
	
	@Override
	public int getMetadata() {
		return metadata;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getColor() {
		return color;
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
