package info.u_team.u_mod.tilentity.pulverizer;

import java.util.ArrayList;

import info.u_team.u_mod.recipe.InputStack;
import net.minecraft.item.ItemStack;

public class RecipeManagerPulverizer {
	
	private static ArrayList<RecipePulverizer> recipes = new ArrayList<>();
	
	public static void add(RecipePulverizer recipe) {
		if (recipes.stream().anyMatch(recipes -> recipes.getInput().equals(recipe.getInput()))) {
			throw new IllegalArgumentException("You cant define the same inputs for more than one recipe.");
		}
		recipes.add(recipe);
	}
	
	 static ArrayList<InputStack> input_dictionary = new ArrayList<>();
	 static ArrayList<ItemStack> primary_output_dictionary = new ArrayList<>();
	 static ArrayList<ItemStack> secondary_output_dictionary = new ArrayList<>();
	//
	// public static void addRecipe(InputStack input, ItemStack output, @Nullable
	// ItemStack second_output) {
	// input_dictionary.add(input);
	// primary_output_dictionary.add(output);
	// secondary_output_dictionary.add(second_output);
	// }
	
}
