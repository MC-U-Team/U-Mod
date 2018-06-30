package info.u_team.u_mod.resources;

import java.util.*;

import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.util.NonNullListCustom;
import net.minecraft.util.NonNullList;

public enum EnumResources {
	
	ALUMINUM(BlockType.BLOCK1, "aluminium"),
	BERYLLIUM(BlockType.BLOCK1, "beryllium"),
	CADMIUM(BlockType.BLOCK1, "cadmium"),
	CHROMIUM(BlockType.BLOCK1, "chromium"),
	COBALT(BlockType.BLOCK1, "cobalt"),
	COPPER(BlockType.BLOCK1, "copper"),
	GALLIUM(BlockType.BLOCK1, "gallium"),
	GRAPHITE(BlockType.BLOCK1, "graphite"),
	IRIDIUM(BlockType.BLOCK1, "iridium"),
	LEAD(BlockType.BLOCK1, "lead"),
	MAGNESIUM(BlockType.BLOCK1, "magnesium"),
	MOLYBDENUM(BlockType.BLOCK1, "molybdenum"),
	NICKEL(BlockType.BLOCK1, "nickel"),
	PALLADIUM(BlockType.BLOCK1, "palladium"),
	PLATINUM(BlockType.BLOCK1, "platinum"),
	PLUTONIUM(BlockType.BLOCK1, "plutonium"),
	
	POTASSIUM(BlockType.BLOCK2, "potassium"),
	SILVER(BlockType.BLOCK2, "silver"),
	SODIUM(BlockType.BLOCK2, "sodium"),
	TANTALUM(BlockType.BLOCK2, "tantalum"),
	TIN(BlockType.BLOCK2, "tin"),
	TUNGSTEN(BlockType.BLOCK2, "tungsten"),
	URANIUM(BlockType.BLOCK2, "uranium"),
	VANADIUM(BlockType.BLOCK2, "vanadium"),
	ZINC(BlockType.BLOCK2, "zinc"),
	ZIRCONIUM(BlockType.BLOCK2, "zirconium");
	
	private BlockType blocktype;
	private String name;
	
	private EnumResources(BlockType blocktype, String name) {
		this.blocktype = blocktype;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public BlockType getBlockType() {
		return blocktype;
	}
	
	public static NonNullList<IUMetaType> createBlockList(BlockType type) {
		List<IUMetaType> list = new ArrayList<>();
		for (EnumResources entry : values()) {
			if (entry.getBlockType() == type) {
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
	
	public static NonNullList<IUMetaType> createItemList() {
		List<IUMetaType> list = new ArrayList<>();
		for (EnumResources entry : values()) {
			list.add(new IUMetaType() {
				
				@Override
				public String getName() {
					return entry.getName();
				}
			});
		}
		return new NonNullListCustom<>(list, null);
	}
	
	public static enum BlockType {
		BLOCK1,
		BLOCK2;
	}
	
}
