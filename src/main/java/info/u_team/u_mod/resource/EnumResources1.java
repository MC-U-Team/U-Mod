package info.u_team.u_mod.resource;

import info.u_team.u_mod.api.IColored;
import info.u_team.u_team_core.api.IMetaType;

public enum EnumResources1 implements IMetaType, IColored {
	
	ALUMINUM(0, "aluminium", 0xF9F9F9),
	BERYLLIUM(1, "beryllium", 0xA9B2BA),
	CADMIUM(2, "cadmium", 0x686166),
	CHROMIUM(3, "chromium", 0xCEDFE8),
	COBALT(4, "cobalt", 0x4545D3),
	COPPER(5, "copper", 0xD17B0A),
	GALLIUM(6, "gallium", 0xE0A1C3),
	GRAPHITE(7, "graphite", 0x332F2E),
	IRIDIUM(8, "iridium", 0xEAEFEF),
	LEAD(9, "lead", 0x63639B),
	MAGNESIUM(10, "magnesium", 0xF7F7F9),
	MOLYBDENUM(11, "molybdenum", 0xC7DFE8),
	NICKEL(12, "nickel", 0xA1C693),
	PALLADIUM(13, "palladium", 0xEEEFAE),
	PLATINUM(14, "platinum", 0xE8DBB9),
	PLUTONIUM(15, "plutonium", 0x75915A);
	
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
