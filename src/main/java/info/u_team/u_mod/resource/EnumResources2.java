package info.u_team.u_mod.resource;

import info.u_team.u_team_core.api.IUMetaType;

public enum EnumResources2 implements IUMetaType {
	
	POTASSIUM(0, "potassium"),
	SILVER(1, "silver"),
	SODIUM(2, "sodium"),
	TANTALUM(3, "tantalum"),
	TIN(4, "tin"),
	TUNGSTEN(5, "tungsten"),
	URANIUM(6, "uranium"),
	VANADIUM(7, "vanadium"),
	ZINC(8, "zinc"),
	ZIRCONIUM(9, "zirconium");
	
	private int metadata;
	private String name;
	
	private EnumResources2(int metadata, String name) {
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
	
	private static final EnumResources2[] META_LOOKUP = new EnumResources2[values().length];
	
	static {
		for (EnumResources2 entry : values()) {
			META_LOOKUP[entry.getMetadata()] = entry;
		}
	}
	
	public static EnumResources2 byMetadata(int meta) {
		if (meta < 0 || meta >= META_LOOKUP.length) {
			meta = 0;
		}
		
		return META_LOOKUP[meta];
	}
	
}
