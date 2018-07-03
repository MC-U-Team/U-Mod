
package info.u_team.u_mod.resource;

import info.u_team.u_mod.api.IColored;
import info.u_team.u_team_core.api.IMetaType;

public enum EnumAlloys1 implements IMetaType, IColored {
	
	ALNICO(0, "alnico", 0xD6B58B),
	BERYLLIUMCOPPER(1, "berylliumcopper", 0xEF9723),
	BRASS(2, "brass", 0xF2D130),
	BRONZE(3, "bronze", 0xF9801D),
	CONSTANTAN(4, "constantan", 0xB3C5D6),
	ELECTRUM(5, "electrum", 0xC6A925),
	FERNICO(6, "fernico", 0xE3F2EF),
	FERRO(7, "ferro", 0xBA9F9A),
	INVAR(8, "invar", 0xC8CE96),
	NICHROME(9, "nichrome", 0xC0C0C0),
	PIGIRON(10, "pigiron", 0x6B6E77),
	STEEL(11, "steel", 0x798287);
	
	private int metadata;
	private String name;
	private int color;
	
	private EnumAlloys1(int metadata, String name, int color) {
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
	
	public static final EnumAlloys1[] VALUES = new EnumAlloys1[values().length];
	
	static {
		for (EnumAlloys1 entry : values()) {
			VALUES[entry.getMetadata()] = entry;
		}
	}
	
	public static EnumAlloys1 byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		
		return VALUES[meta];
	}
	
}
