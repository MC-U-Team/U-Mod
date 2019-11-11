package info.u_team.u_mod.util.recipe;

import java.util.Optional;
import java.util.stream.IntStream;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class RecipeCache<T extends IRecipe<IInventory>> {
	
	private final IRecipeType<T> recipeType;
	private final int ingredientSize;
	private final NonNullList<ItemStack> failedMatch;
	
	private T currentRecipe;
	
	public RecipeCache(IRecipeType<T> recipeType, int ingredientSize) {
		this.recipeType = recipeType;
		this.ingredientSize = ingredientSize;
		failedMatch = NonNullList.withSize(ingredientSize, ItemStack.EMPTY);
	}
	
	public Optional<T> getRecipe(World world, RecipeWrapper recipeWrapper) {
		if (ingredientSize != recipeWrapper.getSizeInventory()) { // Just fail if the inventory sizes do not match
			throw new IllegalStateException("Recipe inventory is not the same size as defined ingredient size");
		}
		
		if (recipeWrapper.isEmpty() || equalsMatchFailed(recipeWrapper)) {
			return Optional.empty();
		} else if (currentRecipe != null && currentRecipe.matches(recipeWrapper, world)) {
			return Optional.of(currentRecipe);
		} else {
			currentRecipe = world.getRecipeManager().getRecipe(recipeType, recipeWrapper, world).orElse(null);
			if (currentRecipe == null) {
				updateMatchFailed(recipeWrapper);
			} else {
				failedMatch.clear();
			}
			return Optional.ofNullable(currentRecipe);
		}
	}
	
	private boolean equalsMatchFailed(IInventory inventory) {
		return IntStream.range(0, inventory.getSizeInventory()).allMatch(index -> ItemStack.areItemStacksEqual(inventory.getStackInSlot(index), failedMatch.get(index)));
	}
	
	private void updateMatchFailed(IInventory inventory) {
		IntStream.range(0, inventory.getSizeInventory()).forEach(index -> failedMatch.set(index, inventory.getStackInSlot(index)));
	}
	
}
