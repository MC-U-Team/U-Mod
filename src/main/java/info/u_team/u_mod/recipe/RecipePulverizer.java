package info.u_team.u_mod.recipe;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.util.NonNullSlot;
import net.minecraft.item.ItemStack;

public class RecipePulverizer implements IMachineRecipe {
	
	private NonNullSlot<IRecipeIngredient> input;
	private NonNullSlot<IRecipeOutput> output;
	
	public RecipePulverizer(ItemStack input, ItemStack firstoutput) {
		this(input, firstoutput, ItemStack.EMPTY, ItemStack.EMPTY);
	}
	
	public RecipePulverizer(ItemStack input, ItemStack firstoutput, ItemStack secondoutput) {
		this(input, firstoutput, secondoutput, ItemStack.EMPTY);
	}
	
	public RecipePulverizer(ItemStack input, ItemStack firstoutput, ItemStack secondoutput, ItemStack thirdoutput) {
		this.input = new NonNullSlot<>(new IngredientItemStack(input));
		this.output = new NonNullSlot<>(new OutputItemStack(firstoutput), new OutputItemStack(secondoutput), new OutputItemStack(thirdoutput));
	}
	
	public RecipePulverizer(String input, ItemStack firstoutput) {
		this(input, firstoutput, ItemStack.EMPTY, ItemStack.EMPTY);
	}
	
	public RecipePulverizer(String input, ItemStack firstoutput, ItemStack secondoutput) {
		this(input, firstoutput, secondoutput, ItemStack.EMPTY);
	}
	
	public RecipePulverizer(String input, ItemStack firstoutput, ItemStack secondoutput, ItemStack thirdoutput) {
		this.input = new NonNullSlot<>(new IngredientItemStack(input));
		this.output = new NonNullSlot<>(new OutputItemStack(firstoutput), new OutputItemStack(secondoutput), new OutputItemStack(thirdoutput));
	}
	
	@Override
	public NonNullSlot<IRecipeIngredient> getInput() {
		return input;
	}
	
	@Override
	public NonNullSlot<IRecipeOutput> getOutput() {
		return output;
	}
	
}
