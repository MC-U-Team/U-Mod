package info.u_team.u_mod.resource;

import com.google.common.collect.ImmutableList;

import info.u_team.u_mod.api.resource.IOreResource;
import info.u_team.u_team_core.api.IUMetaType;

public enum EnumResources implements IOreResource {
	
	ALUMINUM(BlockType.BLOCK1, 0, 0, "aluminium"),
	BERYLLIUM(BlockType.BLOCK1, 1, 1, "beryllium"),
	CADMIUM(BlockType.BLOCK1, 2, 2, "cadmium"),
	CHROMIUM(BlockType.BLOCK1, 3, 3, "chromium"),
	COBALT(BlockType.BLOCK1, 4, 4, "cobalt"),
	COPPER(BlockType.BLOCK1, 5, 5, "copper"),
	GALLIUM(BlockType.BLOCK1, 6, 6, "gallium"),
	GRAPHITE(BlockType.BLOCK1, 7, 7, "graphite"),
	IRIDIUM(BlockType.BLOCK1, 8, 8, "iridium"),
	LEAD(BlockType.BLOCK1, 9, 9, "lead"),
	MAGNESIUM(BlockType.BLOCK1, 10, 10, "magnesium"),
	MOLYBDENUM(BlockType.BLOCK1, 11, 11, "molybdenum"),
	NICKEL(BlockType.BLOCK1, 12, 12, "nickel"),
	PALLADIUM(BlockType.BLOCK1, 13, 13, "palladium"),
	PLATINUM(BlockType.BLOCK1, 14, 14, "platinum"),
	PLUTONIUM(BlockType.BLOCK1, 15, 15, "plutonium"),
	
	POTASSIUM(BlockType.BLOCK2, 0, 16, "potassium"),
	SILVER(BlockType.BLOCK2, 1, 17, "silver"),
	SODIUM(BlockType.BLOCK2, 2, 18, "sodium"),
	TANTALUM(BlockType.BLOCK2, 3, 19, "tantalum"),
	TIN(BlockType.BLOCK2, 4, 20, "tin"),
	TUNGSTEN(BlockType.BLOCK2, 5, 21, "tungsten"),
	URANIUM(BlockType.BLOCK2, 6, 22, "uranium"),
	VANADIUM(BlockType.BLOCK2, 7, 23, "vanadium"),
	ZINC(BlockType.BLOCK2, 8, 24, "zinc"),
	ZIRCONIUM(BlockType.BLOCK2, 9, 25, "zirconium");
	
	private BlockType blocktype;
	private int blockmeta, itemmeta;
	private String name;
	
	private EnumResources(BlockType blocktype, int blockmeta, int itemmeta, String name) {
		this.blocktype = blocktype;
		this.blockmeta = blockmeta;
		this.itemmeta = itemmeta;
		this.name = name;
	}
	
	@Override
	public int getBlockMeta() {
		return blockmeta;
	}
	
	@Override
	public int getItemMeta() {
		return itemmeta;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public BlockType getBlockType() {
		return blocktype;
	}
	
	public static enum BlockType {
		BLOCK1,
		BLOCK2;
	}
	
	public static final ImmutableList<IUMetaType> BLOCK1_LIST, BLOCK2_LIST, ITEM_LIST;
	
	static {
		
		IUMetaType[] block1 = new IUMetaType[16], block2 = new IUMetaType[10], item = new IUMetaType[26];
		
		for (EnumResources entry : values()) {
			if (entry.getBlockType() == BlockType.BLOCK1) {
				block1[entry.getBlockMeta()] = entry;
			} else if (entry.getBlockType() == BlockType.BLOCK2) {
				block2[entry.getBlockMeta()] = entry;
			}
			item[entry.getItemMeta()] = entry;
		}
		
		BLOCK1_LIST = ImmutableList.copyOf(block1);
		BLOCK2_LIST = ImmutableList.copyOf(block2);
		ITEM_LIST = ImmutableList.copyOf(item);
	}
	
	public static EnumResources byName(String name) {
		for (EnumResources entry : values()) {
			if (entry.getName().equals(name)) {
				return entry;
			}
		}
		return null;
	}
	
}
