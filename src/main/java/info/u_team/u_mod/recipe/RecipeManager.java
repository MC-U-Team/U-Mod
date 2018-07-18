package info.u_team.u_mod.recipe;

import java.util.ArrayList;

import info.u_team.u_mod.recipe.machine.*;

public class RecipeManager {
	
	private static ArrayList<RecipeAlloyFurnace> alloyfurnace = new ArrayList<>();
	private static ArrayList<RecipeEnricher> enricher = new ArrayList<>();
	private static ArrayList<RecipeFurnace> furnace = new ArrayList<>();
	private static ArrayList<RecipePress> press = new ArrayList<>();
	private static ArrayList<RecipePulverizer> pulverizer = new ArrayList<>();
	
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
	
	public static ArrayList<RecipeAlloyFurnace> getAlloyFurnaceRecipes() {
		return alloyfurnace;
	}
	
	public static ArrayList<RecipeEnricher> getEnricherRecipes() {
		return enricher;
	}
	
	public static ArrayList<RecipeFurnace> getFurnaceRecipes() {
		return furnace;
	}
	
	public static ArrayList<RecipePress> getPressRecipes() {
		return press;
	}
	
	public static ArrayList<RecipePulverizer> getPulverizerRecipes() {
		return pulverizer;
	}
}
