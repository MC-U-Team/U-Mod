package info.u_team.u_mod.integration.jei.press;

import info.u_team.u_mod.recipe.machine.RecipePress;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RecipeWrapperPress implements IRecipeWrapper {
	
	private RecipePress recipe;
	
	public RecipeWrapperPress(RecipePress recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, recipe.getJeiItemIngredients());
		ingredients.setOutputs(ItemStack.class, recipe.getJeiItemOutputs());
	}
	
}
