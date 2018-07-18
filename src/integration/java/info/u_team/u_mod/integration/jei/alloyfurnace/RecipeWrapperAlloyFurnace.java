package info.u_team.u_mod.integration.jei.alloyfurnace;

import info.u_team.u_mod.recipe.machine.RecipeAlloyFurnace;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RecipeWrapperAlloyFurnace implements IRecipeWrapper {
	
	private RecipeAlloyFurnace recipe;
	
	public RecipeWrapperAlloyFurnace(RecipeAlloyFurnace recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, recipe.getJeiItemIngredients());
		ingredients.setOutputs(ItemStack.class, recipe.getJeiItemOutputs());
	}
	
}
