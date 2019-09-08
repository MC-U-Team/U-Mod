package info.u_team.u_mod.type;

import net.minecraft.item.Rarity;

public enum Crate {
	
	SMALL("small", Rarity.COMMON, 5, 3, 44, 24, 8, 82, 176, 164);
	
	private final String name;
	private final Rarity rarity;
	private final int inventoryWidth, inventoryHeight;
	private final int slotBackpackX, slotBackpackY;
	private final int slotPlayerX, slotPlayerY;
	private final int textureSizeX, textureSizeY;
	
	private Crate(String name, Rarity rarity, int inventoryWidth, int inventoryHeight, int slotBackpackX, int slotBackpackY, int slotPlayerX, int slotPlayerY, int textureSizeX, int textureSizeY) {
		this.name = name;
		this.rarity = rarity;
		this.inventoryWidth = inventoryWidth;
		this.inventoryHeight = inventoryHeight;
		this.slotBackpackX = slotBackpackX;
		this.slotBackpackY = slotBackpackY;
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
	
	public int getInventoryWidth() {
		return inventoryWidth;
	}
	
	public int getInventoryHeight() {
		return inventoryHeight;
	}
	
	public int getInventorySize() {
		return inventoryWidth * inventoryHeight;
	}
	
	public int getSlotBackpackX() {
		return slotBackpackX;
	}
	
	public int getSlotBackpackY() {
		return slotBackpackY;
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
