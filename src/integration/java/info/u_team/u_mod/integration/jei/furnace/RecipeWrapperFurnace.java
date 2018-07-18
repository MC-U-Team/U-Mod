package info.u_team.u_mod.integration.jei.furnace;

import info.u_team.u_mod.recipe.machine.RecipeFurnace;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RecipeWrapperFurnace implements IRecipeWrapper {
	
	private RecipeFurnace recipe;
	
	public RecipeWrapperFurnace(RecipeFurnace recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, recipe.getJeiItemIngredients());
		ingredients.setOutputs(ItemStack.class, recipe.getJeiItemOutputs());
	}
	
}
