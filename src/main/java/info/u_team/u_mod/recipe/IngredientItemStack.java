package info.u_team.u_mod.recipe;

import info.u_team.u_mod.util.ItemUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class IngredientItemStack {
	
	private NonNullList<ItemStack> stacks;
	
	private int count;
	
	public IngredientItemStack(ItemStack stack) {
		if (stack == null) {
			throw new IllegalStateException("Stack can't be null. Use ItemStack.EMPTY");
		}
		count = stack.getCount();
		stacks = NonNullList.withSize(1, stack);
	}
	
	public IngredientItemStack(String oredict, int amount) {
		count = amount;
		stacks = OreDictionary.getOres(oredict);
		if (stacks.isEmpty()) {
			throw new IllegalStateException("Ore in Oredictionary not found");
		}
	}
	
	public NonNullList<ItemStack> getItemStack() {
		return stacks;
	}
	
	public int getCount() {
		return count;
	}
	
	public boolean containsStack(ItemStack other) {
		return stacks.stream().anyMatch(stack -> ItemUtil.areItemStacksEqualIgnoreAmount(stack, other));
	}
	
	public boolean containsStackCountMatchOrHigher(ItemStack other) {
		return stacks.stream().anyMatch(stack -> ItemUtil.areItemStacksEqualIgnoreAmount(stack, other) && other.getCount() >= count);
	}
	
}
