package info.u_team.u_mod.recipe;

import java.util.ArrayList;

import info.u_team.u_mod.api.IMachineRecipe;

public class RecipeManager {
	
	private static ArrayList<RecipePulverizer> pulverizer = new ArrayList<>();
	
	public static void registerPulverizer(RecipePulverizer recipe) {
		checkInputs(recipe);
		pulverizer.add(recipe);
	}
	
	private static void checkInputs(IMachineRecipe recipe) {
		if (pulverizer.stream().anyMatch(recipes -> recipes.getInput().equals(recipe.getInput()))) {
			throw new IllegalArgumentException("You cant define the same inputs for more than one recipe.");
		}
	}
	
	public static ArrayList<RecipePulverizer> getPulverizerRecipes() {
		return pulverizer;
	}
}
