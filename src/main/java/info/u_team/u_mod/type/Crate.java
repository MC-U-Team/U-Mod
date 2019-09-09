package info.u_team.u_mod.type;

import net.minecraft.item.Rarity;

public enum Crate {
	
	SMALL("small", Rarity.COMMON, 5, 3, 44, 24, 8, 82, 176, 164);
	
	private final String name;
	private final Rarity rarity;
	private final int slotWidth, slotHeight;
	private final int slotX, slotY;
	private final int slotPlayerX, slotPlayerY;
	private final int textureSizeX, textureSizeY;
	
	private Crate(String name, Rarity rarity, int slotWidth, int slotHeight, int slotX, int slotY, int slotPlayerX, int slotPlayerY, int textureSizeX, int textureSizeY) {
		this.name = name;
		this.rarity = rarity;
		this.slotWidth = slotWidth;
		this.slotHeight = slotHeight;
		this.slotX = slotX;
		this.slotY = slotY;
		this.slotPlayerX = slotPlayerX;
		this.slotPlayerY = slotPlayerY;
		this.textureSizeX = textureSizeX;
		this.textureSizeY = textureSizeY;
	}
	
	public String getName() {
		return name;
	}
	
	public Rarity getRarity() {
		return rarity;
	}
	
	public int getSlotWidth() {
		return slotWidth;
	}
	
	public int getSlotHeight() {
		return slotHeight;
	}
	
	public int getInventorySize() {
		return slotWidth * slotHeight;
	}
	
	public int getSlotX() {
		return slotX;
	}
	
	public int getSlotY() {
		return slotY;
	}
	
	public int getSlotPlayerX() {
		return slotPlayerX;
	}
	
	public int getSlotPlayerY() {
		return slotPlayerY;
	}
	
	public int getTextureSizeX() {
		return textureSizeX;
	}
	
	public int getTextureSizeY() {
		return textureSizeY;
	}
	
}
