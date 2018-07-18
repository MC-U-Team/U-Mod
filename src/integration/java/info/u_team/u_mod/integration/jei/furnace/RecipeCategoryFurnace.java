package info.u_team.u_mod.integration.jei.furnace;

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

public class RecipeCategoryFurnace implements IRecipeCategory<RecipeWrapperFurnace> {
	
	private IDrawable background;
	private IDrawableAnimated progressbar;
	private ResourceLocation PULVERIZER = new ResourceLocation(UConstants.MODID, "textures/gui/pulverizer.png");
	
	public RecipeCategoryFurnace(IGuiHelper guiHelper) {
		this.background = guiHelper.drawableBuilder(PULVERIZER, 26, 17, 120, 60).build();
		this.progressbar = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(PULVERIZER, 0, 166, 64, 7), 100, StartDirection.LEFT, false);
	}
	
	@Override
	public String getUid() {
		return JeiPlugin.furnaceID;
	}
	
	@Override
	public String getTitle() {
		return UBlocks.furnace.getLocalizedName();
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
		this.progressbar.draw(minecraft, 21, 11);
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (mouseX >= 21 && mouseX <= 85 && mouseY >= 11 && mouseY <= 18) {
			return ImmutableList.of(ChatFormatting.RED + "Needs energy!", ChatFormatting.BLUE + "See 'Energy' tab!");
		}
		return ImmutableList.of();
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RecipeWrapperFurnace recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 3, 5);
		guiItemStacks.init(1, false, 98, 36);
		
		guiItemStacks.set(ingredients);
	}
	
}
