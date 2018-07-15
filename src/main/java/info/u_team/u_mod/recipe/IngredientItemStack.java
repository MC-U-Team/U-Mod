package info.u_team.u_mod.recipe;

import info.u_team.u_mod.api.IRecipeIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class IngredientItemStack implements IRecipeIngredient {
	
	private NonNullList<ItemStack> stacks;
	
	private String ore = null;
	
	public IngredientItemStack(ItemStack stack) {
		if (stack == null) {
			throw new IllegalStateException("Stack can't be null. Use ItemStack.EMPTY");
		}
		stacks = NonNullList.withSize(1, stack);
	}
	
	public IngredientItemStack(String oredict) {
		stacks = OreDictionary.getOres(oredict);
		ore = oredict;
		if (stacks.isEmpty()) {
			throw new IllegalStateException("Ore in Oredictionary not found");
		}
	}
	
	public NonNullList<ItemStack> getItemStack() {
		return stacks;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IngredientItemStack)) {
			return false;
		}
		IngredientItemStack other = ((IngredientItemStack) obj);
		if (ore == null && other.ore == null) {
			ItemStack stack = stacks.get(0);
			ItemStack otherstack = other.stacks.get(0);
			return ItemStack.areItemsEqual(stack, otherstack) && ItemStack.areItemStackTagsEqual(stack, otherstack);
		} else if (ore != null && other.ore != null) {
			return ore == other.ore;
		} else if (ore == null && other.ore != null) {
			ItemStack stack = stacks.get(0);
			return other.stacks.stream().anyMatch(otherstack -> ItemStack.areItemsEqual(stack, otherstack) && ItemStack.areItemStackTagsEqual(stack, otherstack));
		} else if (ore != null && other.ore == null) {
			ItemStack otherstack = other.stacks.get(0);
			return stacks.stream().anyMatch(stack -> ItemStack.areItemsEqual(stack, otherstack) && ItemStack.areItemStackTagsEqual(stack, otherstack));
		}
		return false;
	}
}
