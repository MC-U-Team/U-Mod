package info.u_team.u_mod.integration.jei.alloyfurnace;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.realmsclient.gui.ChatFormatting;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.integration.jei.JeiPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RecipeCategoryAlloyFurnace implements IRecipeCategory<RecipeWrapperAlloyFurnace> {
	
	private IDrawable background;
	private IDrawableAnimated progressbar;
	private ResourceLocation ALLOY_FURNACE = new ResourceLocation(UConstants.MODID, "textures/gui/alloy_furnace.png");
	
	public RecipeCategoryAlloyFurnace(IGuiHelper guiHelper) {
		this.background = guiHelper.drawableBuilder(ALLOY_FURNACE, 47, 3, 82, 72).build();
		this.progressbar = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(ALLOY_FURNACE, 176, 0, 72, 30), 100, StartDirection.TOP, false);
	}
	
	@Override
	public String getUid() {
		return JeiPlugin.alloyfurnaceID;
	}
	
	@Override
	public String getTitle() {
		return UBlocks.alloy_furnace.getLocalizedName();
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
	public void drawExtras(Minecraft minecraft) {
		this.progressbar.draw(minecraft, 5, 22);
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (mouseX >= 5 && mouseX <= 77 && mouseY >= 22 && mouseY <= 55) {
			return ImmutableList.of(ChatFormatting.RED + "Needs energy!", ChatFormatting.BLUE + "See 'Energy' tab!");
		}
		return ImmutableList.of();
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RecipeWrapperAlloyFurnace recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 3, 3);
		guiItemStacks.init(1, true, 32, 3);
		guiItemStacks.init(2, true, 61, 3);
		guiItemStacks.init(3, false, 32, 51);
		
		guiItemStacks.set(ingredients);
	}
	
}
