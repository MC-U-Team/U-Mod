package info.u_team.u_mod.recipe;

import java.util.ArrayList;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.recipe.machine.*;

public class RecipeManager {
	
	private static ArrayList<IMachineRecipe> alloyfurnace = new ArrayList<>();
	private static ArrayList<IMachineRecipe> enricher = new ArrayList<>();
	private static ArrayList<IMachineRecipe> furnace = new ArrayList<>();
	private static ArrayList<IMachineRecipe> press = new ArrayList<>();
	private static ArrayList<IMachineRecipe> pulverizer = new ArrayList<>();
	
	public static void registerAlloyFurnace(RecipeAlloyFurnace recipe) {
		alloyfurnace.add(recipe);
	}
	
	public static void registerEnricher(RecipeEnricher recipe) {
		enricher.add(recipe);
	}
	
	public static void registerFurnace(RecipeFurnace recipe) {
		furnace.add(recipe);
	}
	
	public static void registerPress(RecipePress recipe) {
		press.add(recipe);
	}
	
	public static void registerPulverizer(RecipePulverizer recipe) {
		pulverizer.add(recipe);
	}
	
	public static ArrayList<IMachineRecipe> getAlloyFurnaceRecipes() {
		return alloyfurnace;
	}
	
	public static ArrayList<IMachineRecipe> getEnricherRecipes() {
		return enricher;
	}
	
	public static ArrayList<IMachineRecipe> getFurnaceRecipes() {
		return furnace;
	}
	
	public static ArrayList<IMachineRecipe> getPressRecipes() {
		return press;
	}
	
	public static ArrayList<IMachineRecipe> getPulverizerRecipes() {
		return pulverizer;
	}
}
