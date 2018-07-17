package info.u_team.u_mod.recipe;

import net.minecraft.item.ItemStack;

public class OutputItemStack {
	
	private ItemStack stack;
	
	public OutputItemStack(ItemStack stack) {
		if (stack == null) {
			throw new IllegalStateException("Stack can't be null. Use ItemStack.EMPTY");
		}
		this.stack = stack;
	}
	
	public ItemStack getItemStack() {
		return stack.copy();
	}
	
}
