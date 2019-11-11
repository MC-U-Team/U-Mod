package info.u_team.u_mod.util.recipe;

import java.util.function.*;

import info.u_team.u_mod.recipe.MachineRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;

public class RecipeData<T extends IRecipe<IInventory>> {
	
	private final Function<T, Integer> totalTimeFunction;
	private final Function<T, Integer> consumptionOnStartFunction;
	private final Function<T, Integer> consumptionPerTickFunction;
	private final BiFunction<T, IInventory, NonNullList<ItemStack>> recipeOutputsFunction;
	
	public RecipeData(Function<T, Integer> totalTimeFunction, Function<T, Integer> consumptionOnStartFunction, Function<T, Integer> consumptionPerTickFunction, BiFunction<T, IInventory, NonNullList<ItemStack>> recipeOutputsFunction) {
		this.totalTimeFunction = totalTimeFunction;
		this.consumptionOnStartFunction = consumptionOnStartFunction;
		this.consumptionPerTickFunction = consumptionPerTickFunction;
		this.recipeOutputsFunction = recipeOutputsFunction;
	}
	
	public int getTotalTime(T recipe) {
		return totalTimeFunction.apply(recipe);
	}
	
	public int getConsumptionOnStart(T recipe) {
		return consumptionOnStartFunction.apply(recipe);
	}
	
	public int getConsumptionPerTick(T recipe) {
		return consumptionPerTickFunction.apply(recipe);
	}
	
	public NonNullList<ItemStack> getRecipeOutputs(T recipe, IInventory inventory) {
		return recipeOutputsFunction.apply(recipe, inventory);
	}
	
	public static <X extends MachineRecipe> RecipeData<X> getBasicMachine() {
		return new RecipeData<>(MachineRecipe::getTotalTime, MachineRecipe::getConsumptionOnStart, MachineRecipe::getConsumptionPerTick, MachineRecipe::getOutput);
	}
	
	public static <X extends AbstractCookingRecipe> RecipeData<X> getBasicCooking(int defaultConsumptionOnStart, int defaultConsumptionPerTick) {
		return new RecipeData<>(AbstractCookingRecipe::getCookTime, recipe -> defaultConsumptionOnStart, recipe -> defaultConsumptionPerTick, (recipe, inventory) -> NonNullList.from(ItemStack.EMPTY, recipe.getCraftingResult(inventory)));
	}
}
