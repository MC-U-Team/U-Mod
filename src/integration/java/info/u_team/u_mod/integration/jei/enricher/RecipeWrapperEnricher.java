package info.u_team.u_mod.integration.jei.enricher;

import info.u_team.u_mod.recipe.machine.RecipeEnricher;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RecipeWrapperEnricher implements IRecipeWrapper {
	
	private RecipeEnricher recipe;
	
	public RecipeWrapperEnricher(RecipeEnricher recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, recipe.getJeiItemIngredients());
		ingredients.setOutputs(ItemStack.class, recipe.getJeiItemOutputs());
	}
	
}
