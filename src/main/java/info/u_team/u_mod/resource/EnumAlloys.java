
package info.u_team.u_mod.resource;

public enum EnumAlloys {
	
	ALNICO(BlockType.BLOCK1, "alnico"),
	BERYLLIUMCOPPER(BlockType.BLOCK1, "berylliumcopper"),
	BRASS(BlockType.BLOCK1, "brass"),
	BRONZE(BlockType.BLOCK1, "bronze"),
	CONSTANTAN(BlockType.BLOCK1, "constantan"),
	ELECTRUM(BlockType.BLOCK1, "electrum"),
	FERNICO(BlockType.BLOCK1, "fernico"),
	FERRO(BlockType.BLOCK1, "ferro"),
	INVAR(BlockType.BLOCK1, "invar"),
	NICHROME(BlockType.BLOCK1, "nichrome"),
	PIGIRON(BlockType.BLOCK1, "pigiron"),
	STEEL(BlockType.BLOCK1, "steel");
	
	private BlockType blocktype;
	private String name;
	
	private EnumAlloys(BlockType blocktype, String name) {
		this.blocktype = blocktype;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public BlockType getBlockType() {
		return blocktype;
	}
	
	public static enum BlockType {
		BLOCK1;
	}
	
}
