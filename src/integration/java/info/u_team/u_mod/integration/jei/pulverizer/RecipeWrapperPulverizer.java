package info.u_team.u_mod.integration.jei.pulverizer;

import info.u_team.u_mod.recipe.machine.RecipePulverizer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RecipeWrapperPulverizer implements IRecipeWrapper {
	
	private RecipePulverizer recipe;
	
	public RecipeWrapperPulverizer(RecipePulverizer recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, recipe.getJeiItemIngredients());
		ingredients.setOutputs(ItemStack.class, recipe.getJeiItemOutputs());
	}
	
}
