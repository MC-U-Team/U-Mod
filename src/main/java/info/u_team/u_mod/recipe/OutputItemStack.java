package info.u_team.u_mod.recipe;

import info.u_team.u_mod.api.IRecipeOutput;
import net.minecraft.item.ItemStack;

public class OutputItemStack implements IRecipeOutput {
	
	private ItemStack stack;
	
	public OutputItemStack(ItemStack stack) {
		if (stack == null) {
			throw new IllegalStateException("Stack can't be null. Use ItemStack.EMPTY");
		}
		this.stack = stack;
	}
	
	public ItemStack getItemStack() {
		return stack;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OutputItemStack)) {
			return false;
		}
		ItemStack other = ((OutputItemStack) obj).stack;
		return ItemStack.areItemsEqual(stack, other) && ItemStack.areItemStackTagsEqual(stack, other);
	}
	
}
