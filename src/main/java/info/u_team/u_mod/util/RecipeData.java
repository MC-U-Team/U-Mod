package info.u_team.u_mod.util;

import java.util.function.Function;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

public class RecipeData<T extends IRecipe<IInventory>> {
	
	private final Function<T, Integer> totalTimeFunction;
	private final Function<T, NonNullList<ItemStack>> recipeOutputsFunction;
	
	public RecipeData(Function<T, Integer> totalTimeFunction, Function<T, NonNullList<ItemStack>> recipeOutputsFunction) {
		this.totalTimeFunction = totalTimeFunction;
		this.recipeOutputsFunction = recipeOutputsFunction;
	}
	
	public int getTotalTime(T recipe) {
		return totalTimeFunction.apply(recipe);
	}
	
	public NonNullList<ItemStack> getRecipeOutputs(T recipe) {
		return recipeOutputsFunction.apply(recipe);
	}
	
}
