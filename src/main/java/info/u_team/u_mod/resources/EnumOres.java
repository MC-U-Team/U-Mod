package info.u_team.u_mod.resources;

import java.util.ArrayList;
import java.util.List;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.util.NonNullListCustom;
import net.minecraft.util.NonNullList;

public enum EnumOres {
	
	ALUMINUM(Type.ORE1, "aluminium"),
	BERYLLIUM(Type.ORE1, "beryllium"),
	CADMIUM(Type.ORE1, "cadmium"),
	CHROMIUM(Type.ORE1, "chromium"),
	COBALT(Type.ORE1, "cobalt"),
	COPPER(Type.ORE1, "copper"),
	GALLIUM(Type.ORE1, "gallium"),
	GRAPHITE(Type.ORE1, "graphite"),
	IRIDIUM(Type.ORE1, "iridium"),
	LEAD(Type.ORE1, "lead"),
	MAGNESIUM(Type.ORE1, "magnesium"),
	MOLYBDENUM(Type.ORE1, "molybdenum"),
	NICKEL(Type.ORE1, "nickel"),
	PALLADIUM(Type.ORE1, "palladium"),
	PLATINUM(Type.ORE1, "platinum"),
	PLUTONIUM(Type.ORE1, "plutonium"),
	
	POTASSIUM(Type.ORE2, "potassium"),
	SILVER(Type.ORE2, "silver"),
	SODIUM(Type.ORE2, "sodium"),
	TANTALUM(Type.ORE2, "tantalum"),
	TIN(Type.ORE2, "tin"),
	TUNGSTEN(Type.ORE2, "tungsten"),
	URANIUM(Type.ORE2, "uranium"),
	VANADIUM(Type.ORE2, "vanadium"),
	ZINC(Type.ORE2, "zinc"),
	ZIRCONIUM(Type.ORE2, "zirconium");
	
	private Type type;
	private String name;
	
	private EnumOres(Type type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public static NonNullList<IUMetaType> createList(Type type) {
		List<IUMetaType> list = new ArrayList<>();
		for (EnumOres entry : values()) {
			if (entry.getType() == type) {
				list.add(new IUMetaType() {
					
					@Override
					public String getName() {
						return entry.getName();
					}
				});
			}
		}
		return new NonNullListCustom<>(list, null);
	}
	
	public static enum Type {
		ORE1,
		ORE2;
	}
	
}
