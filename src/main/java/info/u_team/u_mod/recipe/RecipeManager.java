package info.u_team.u_mod.recipe;

import java.util.ArrayList;

import info.u_team.u_mod.recipe.machine.RecipePulverizer;

public class RecipeManager {
	
	private static ArrayList<RecipePulverizer> pulverizer = new ArrayList<>();
	
	public static void registerPulverizer(RecipePulverizer recipe) {
		pulverizer.add(recipe);
	}
	
	public static ArrayList<RecipePulverizer> getPulverizerRecipes() {
		return pulverizer;
	}
}
