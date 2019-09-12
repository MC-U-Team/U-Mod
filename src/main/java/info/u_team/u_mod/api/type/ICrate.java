package info.u_team.u_mod.api.type;

import net.minecraft.item.Rarity;

public interface ICrate {
	
	String getName();
	
	Rarity getRarity();
	
	int getSlotWidth();
	
	int getSlotHeight();
	
	int getInventorySize();
	
	int getSlotX();
	
	int getSlotY();
	
	int getSlotPlayerX();
	
	int getSlotPlayerY();
	
	int getTextureSizeX();
	
	int getTextureSizeY();
	
}