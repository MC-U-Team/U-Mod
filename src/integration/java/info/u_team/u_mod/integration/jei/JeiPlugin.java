package info.u_team.u_mod.integration.jei;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.gui.machine.*;
import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.integration.jei.alloyfurnace.*;
import info.u_team.u_mod.integration.jei.enricher.*;
import info.u_team.u_mod.integration.jei.furnace.*;
import info.u_team.u_mod.integration.jei.press.*;
import info.u_team.u_mod.integration.jei.pulverizer.*;
import info.u_team.u_mod.recipe.RecipeManager;
import info.u_team.u_mod.recipe.machine.*;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {
	
	public static final String alloyfurnaceID = UConstants.MODID + ".alloyfurnace";
	public static final String enricherID = UConstants.MODID + ".enricher";
	public static final String furnaceID = UConstants.MODID + ".furnace";
	public static final String pressID = UConstants.MODID + ".press";
	public static final String pulverizerID = UConstants.MODID + ".pulverizer";
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new RecipeCategoryAlloyFurnace(guiHelper), new RecipeCategoryEnricher(guiHelper), new RecipeCategoryFurnace(guiHelper), new RecipeCategoryPress(guiHelper), new RecipeCategoryPulverizer(guiHelper));
	}
	
	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(RecipeAlloyFurnace.class, RecipeWrapperAlloyFurnace::new, alloyfurnaceID);
		registry.addRecipes(RecipeManager.getAlloyFurnaceRecipes(), alloyfurnaceID);
		registry.addRecipeCatalyst(new ItemStack(UBlocks.alloy_furnace), alloyfurnaceID);
		//registry.addRecipeClickArea(GuiAlloyFurnace.class, 52, 25, 72, 30, alloyfurnaceID); // TODO FIX TABS
		
		registry.handleRecipes(RecipeEnricher.class, RecipeWrapperEnricher::new, enricherID);
		registry.addRecipes(RecipeManager.getEnricherRecipes(), enricherID);
		registry.addRecipeCatalyst(new ItemStack(UBlocks.enricher), enricherID);
		//registry.addRecipeClickArea(GuiEnricher.class, 61, 32, 53, 16, enricherID);
		
		registry.handleRecipes(RecipeFurnace.class, RecipeWrapperFurnace::new, furnaceID);
		registry.addRecipes(RecipeManager.getFurnaceRecipes(), furnaceID);
		registry.addRecipeCatalyst(new ItemStack(UBlocks.furnace), furnaceID);
		//registry.addRecipeClickArea(GuiFurnace.class, 47, 28, 64, 7, furnaceID);
		
		registry.handleRecipes(RecipePress.class, RecipeWrapperPress::new, pressID);
		registry.addRecipes(RecipeManager.getPressRecipes(), pressID);
		registry.addRecipeCatalyst(new ItemStack(UBlocks.press), pressID);
		//registry.addRecipeClickArea(GuiPress.class, 80, 22, 16, 40, pressID);
		
		registry.handleRecipes(RecipePulverizer.class, RecipeWrapperPulverizer::new, pulverizerID);
		registry.addRecipes(RecipeManager.getPulverizerRecipes(), pulverizerID);
		registry.addRecipeCatalyst(new ItemStack(UBlocks.pulverizer), pulverizerID);
		//registry.addRecipeClickArea(GuiPulverizer.class, 47, 28, 64, 7, pulverizerID);
	}
}
