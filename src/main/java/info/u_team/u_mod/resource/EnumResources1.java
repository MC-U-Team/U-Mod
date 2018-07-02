package info.u_team.u_mod.resource;

import info.u_team.u_team_core.api.IUMetaType;

public enum EnumResources1 implements IUMetaType {
	
	ALUMINUM(0, "aluminium"),
	BERYLLIUM(1, "beryllium"),
	CADMIUM(2, "cadmium"),
	CHROMIUM(3, "chromium"),
	COBALT(4, "cobalt"),
	COPPER(5, "copper"),
	GALLIUM(6, "gallium"),
	GRAPHITE(7, "graphite"),
	IRIDIUM(8, "iridium"),
	LEAD(9, "lead"),
	MAGNESIUM(10, "magnesium"),
	MOLYBDENUM(11, "molybdenum"),
	NICKEL(12, "nickel"),
	PALLADIUM(13, "palladium"),
	PLATINUM(14, "platinum"),
	PLUTONIUM(15, "plutonium");
	
	private int metadata;
	private String name;
	
	private EnumResources1(int metadata, String name) {
		this.metadata = metadata;
		this.name = name;
	}
	
	public int getMetadata() {
		return metadata;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	private static final EnumResources1[] META_LOOKUP = new EnumResources1[values().length];
	
	static {
		for (EnumResources1 entry : values()) {
			META_LOOKUP[entry.getMetadata()] = entry;
		}
	}
	
	public static EnumResources1 byMetadata(int meta) {
		if (meta < 0 || meta >= META_LOOKUP.length) {
			meta = 0;
		}
		
		return META_LOOKUP[meta];
	}
	
}
