package info.u_team.u_mod.util;

import net.minecraft.item.ItemStack;

public class ItemUtil {
	
	public static boolean areItemStacksEqualIgnoreAmount(ItemStack a, ItemStack b) {
		return ItemStack.areItemsEqual(a, b) && ItemStack.areItemStackTagsEqual(a, b);
	}
	
}
