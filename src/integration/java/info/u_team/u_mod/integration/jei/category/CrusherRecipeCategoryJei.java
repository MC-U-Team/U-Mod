package info.u_team.u_mod.integration.jei.category;

import java.util.*;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.init.UModBlocks;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_mod.screen.CrusherScreen;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CrusherRecipeCategoryJei implements IRecipeCategory<OneIngredientMachineRecipe> {
	
	public static final ResourceLocation ID = new ResourceLocation(UMod.MODID, "crusher");
	
	private final IDrawable background;
	private final IDrawable icon;
	
	private final String title;
	
	public CrusherRecipeCategoryJei(IGuiHelper helper) {
		title = I18n.format("jei.umod.category.crusher");
		
		background = helper.createDrawable(CrusherScreen.TEXTURE, 0, 0, 176, 174);
		icon = helper.createDrawableIngredient(new ItemStack(UModBlocks.CRUSHER));
	}
	
	@Override
	public ResourceLocation getUid() {
		return ID;
	}
	
	@Override
	public Class<? extends OneIngredientMachineRecipe> getRecipeClass() {
		return OneIngredientMachineRecipe.class;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public IDrawable getIcon() {
		return icon;
	}
	
	@Override
	public void setIngredients(OneIngredientMachineRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputs());
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, OneIngredientMachineRecipe recipe, IIngredients ingredients) {
		final IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		itemStackGroup.init(0, true, 44, 39);
		itemStackGroup.init(1, false, 116, 39);
		
		itemStackGroup.set(0, Arrays.asList(recipe.getIngredients().get(0).getMatchingStacks()));
		itemStackGroup.set(1, recipe.getOutputs().get(0));
	}
	
}
