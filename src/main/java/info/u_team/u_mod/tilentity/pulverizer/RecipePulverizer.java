package info.u_team.u_mod.tilentity.pulverizer;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.recipe.RecipeSlotItemStack;
import info.u_team.u_mod.util.NonNullSlot;
import net.minecraft.item.ItemStack;

public class RecipePulverizer implements IMachineRecipe {
	
	private NonNullSlot<IRecipeSlot> input;
	private NonNullSlot<IRecipeSlot> output;
	
	public RecipePulverizer(ItemStack input, ItemStack firstoutput, ItemStack secondoutput, ItemStack thirdoutput) {
		this.input = new NonNullSlot<>(new RecipeSlotItemStack(input));
		this.output = new NonNullSlot<>(new RecipeSlotItemStack(firstoutput), new RecipeSlotItemStack(secondoutput), new RecipeSlotItemStack(thirdoutput));
	}
	
	@Override
	public NonNullSlot<IRecipeSlot> getInput() {
		return input;
	}
	
	@Override
	public NonNullSlot<IRecipeSlot> getOutput() {
		return output;
	}
	
}
