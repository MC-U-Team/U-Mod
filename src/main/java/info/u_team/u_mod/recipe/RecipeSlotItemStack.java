package info.u_team.u_mod.recipe;

import info.u_team.u_mod.api.IRecipeSlot;
import net.minecraft.item.ItemStack;

public class RecipeSlotItemStack implements IRecipeSlot {
	
	private ItemStack stack;
	
	public RecipeSlotItemStack(ItemStack stack) {
		this.stack = stack;
	}
	
	public ItemStack getItemStack() {
		return stack;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RecipeSlotItemStack)) {
			return false;
		}
		ItemStack other = ((RecipeSlotItemStack) obj).stack;
		return ItemStack.areItemsEqual(stack, other) && ItemStack.areItemStackTagsEqual(stack, other);
	}
	
}
