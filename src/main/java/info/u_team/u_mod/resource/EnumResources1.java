package info.u_team.u_mod.resource;

import info.u_team.u_mod.api.IColored;
import info.u_team.u_team_core.api.IMetaType;

public enum EnumResources1 implements IMetaType, IColored {
	
	ALUMINUM(0, "aluminium", 0xFFAAAA),
	BERYLLIUM(1, "beryllium", 0xFFFFAA),
	CADMIUM(2, "cadmium", 0xFFAAFF),
	CHROMIUM(3, "chromium", 0xFFAAAA),
	COBALT(4, "cobalt", 0xFFAAAA),
	COPPER(5, "copper", 0xFFAAAA),
	GALLIUM(6, "gallium", 0xFFAAAA),
	GRAPHITE(7, "graphite", 0xFFAAAA),
	IRIDIUM(8, "iridium", 0xFFAAAA),
	LEAD(9, "lead", 0xFFAAAA),
	MAGNESIUM(10, "magnesium", 0xFFAAAA),
	MOLYBDENUM(11, "molybdenum", 0xFFAAAA),
	NICKEL(12, "nickel", 0xFFAAAA),
	PALLADIUM(13, "palladium", 0xFFAAAA),
	PLATINUM(14, "platinum", 0xFFAAAA),
	PLUTONIUM(15, "plutonium", 0xFFAAAA);
	
	private int metadata;
	private String name;
	private int color;
	
	private EnumResources1(int metadata, String name, int color) {
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
	
	public static final EnumResources1[] VALUES = new EnumResources1[values().length];
	
	static {
		for (EnumResources1 entry : values()) {
			VALUES[entry.getMetadata()] = entry;
		}
	}
	
	public static EnumResources1 byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		
		return VALUES[meta];
	}
	
}
