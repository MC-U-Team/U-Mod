package info.u_team.u_mod.integration.jei;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.integration.jei.pulverizer.*;
import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.recipe.machine.RecipePulverizer;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RecipeCategoryPulverizer(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(RecipePulverizer.class, RecipeWrapperPulverizer::new, UConstants.MODID + ".pulverizer");
		registry.addRecipes(RecipeManager.getPulverizerRecipes(), UConstants.MODID + ".pulverizer");
		
		registry.addRecipeCatalyst(new ItemStack(UBlocks.pulverizer), UConstants.MODID + ".pulverizer");
	}
}
