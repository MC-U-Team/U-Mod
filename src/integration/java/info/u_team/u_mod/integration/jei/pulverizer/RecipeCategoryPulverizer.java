package info.u_team.u_mod.integration.jei.pulverizer;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.config.Constants;

public class RecipeCategoryPulverizer implements IRecipeCategory<RecipeWrapperPulverizer> {
	
	private IDrawable background;
	
	public RecipeCategoryPulverizer(IGuiHelper guiHelper) {
		background = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 0, 168, 125, 18).addPadding(0, 20, 0, 0).build();
	}
	
	@Override
	public String getUid() {
		return UConstants.MODID + ".pulverizer";
	}
	
	@Override
	public String getTitle() {
		return UBlocks.pulverizer.getLocalizedName();
	}
	
	@Override
	public String getModName() {
		return UConstants.NAME;
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RecipeWrapperPulverizer recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, true, 49, 0);
		guiItemStacks.init(2, false, 107, 0);
		guiItemStacks.init(2, false, 123, 0);
		
		guiItemStacks.set(ingredients);
	}
	
}
