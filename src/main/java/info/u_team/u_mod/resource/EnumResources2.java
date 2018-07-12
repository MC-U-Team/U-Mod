package info.u_team.u_mod.resource;

import info.u_team.u_mod.api.IResource;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public enum EnumResources2 implements IResource {
	
	POTASSIUM(0, "potassium", 0xCE5F5F),
	SILVER(1, "silver", 0xC2E2ED),
	SODIUM(2, "sodium", 0xFFDBEF),
	TANTALUM(3, "tantalum", 0xE6ECF7),
	TIN(4, "tin", 0xFFFFFF),
	TUNGSTEN(5, "tungsten", 0xE2BD85),
	URANIUM(6, "uranium", 0x1CB22E),
	VANADIUM(7, "vanadium", 0x724955),
	ZINC(8, "zinc", 0x504E66),
	ZIRCONIUM(9, "zirconium", 0x315D77);
	
	private int metadata;
	private String name;
	private int color;
	
	private EnumResources2(int metadata, String name, int color) {
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
	
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getBlockState(Block block) {
		return block.getStateFromMeta(getMetadata());
	}
	
	@Override
	public int getEnumCount() {
		return 1;
	}
	
	public static final EnumResources2[] VALUES = new EnumResources2[values().length];
	
	static {
		for (EnumResources2 entry : values()) {
			VALUES[entry.getMetadata()] = entry;
		}
	}
	
	public static EnumResources2 byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		
		return VALUES[meta];
	}
	
}
