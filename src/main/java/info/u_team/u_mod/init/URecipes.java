package info.u_team.u_mod.init;

import static info.u_team.u_team_core.registry.CommonRegistry.registerSmelting;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.recipe.crafting.RecipeShapelessOre;
import info.u_team.u_mod.recipe.machine.*;
import info.u_team.u_mod.resource.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class URecipes {
	
	public static void init() {
		furnace();
		crafting();
		
		// TEST
		
		RecipeManager.registerAlloyFurnace(new RecipeAlloyFurnace(new IngredientItemStack("oreUranium", 2), new IngredientItemStack("oreUranium", 1), new IngredientItemStack(new ItemStack(Blocks.STONE, 4)), new OutputItemStack(new ItemStack(Blocks.IRON_BLOCK, 32)), new ProcessEnergy(200), new ProcessTime(200)));
		
		RecipeManager.registerEnricher(new RecipeEnricher(new IngredientItemStack("oreUranium", 4), new OutputItemStack(new ItemStack(Items.IRON_INGOT, 4)), new ProcessEnergy(100), new ProcessTime(50)));
		
		RecipeManager.registerFurnace(new RecipeFurnace(new IngredientItemStack("oreUranium", 1), new OutputItemStack(new ItemStack(Items.IRON_INGOT, 2)), new ProcessEnergy(100), new ProcessTime(50)));
		
		RecipeManager.registerPress(new RecipePress(new IngredientItemStack("oreUranium", 6), new OutputItemStack(ResourceUtil.get(EnumResources2.URANIUM).getBlockItemStack()), new ProcessEnergy(100), new ProcessTime(150)));
		
		RecipeManager.registerPulverizer(new RecipePulverizer(new IngredientItemStack("oreUranium", 2), new OutputItemStack(new ItemStack(Items.IRON_INGOT, 4)), new ProcessEnergy(100), new ProcessTime(300)));
		RecipeManager.registerPulverizer(new RecipePulverizer(new IngredientItemStack(new ItemStack(Blocks.STONE, 5)), new OutputItemStack(new ItemStack(Items.IRON_INGOT, 16)), new ProcessEnergy(100), new ProcessTime(100)));
	}
	
	private static void crafting() {
		// Resources
		ResourceUtil.iterate(resource -> {
			String name = resource.getName();
			String orename = resource.getCapitalizedName();
			// Ingot <-> Block
			registerCrafting("ingot_block_" + name, new RecipeShapelessOre("resource", "ingot" + orename, 9, resource.getBlockItemStack(1)));
			registerCrafting("block_ingot_" + name, new RecipeShapelessOre("resource", "block" + orename, 1, resource.getIngotItemStack(9)));
			
			// Nugget <-> Ingot
			registerCrafting("nugget_ingot_" + name, new RecipeShapelessOre("resource", "nugget" + orename, 9, resource.getIngotItemStack(1)));
			registerCrafting("ingot_nugget_" + name, new RecipeShapelessOre("resource", "ingot" + orename, 1, resource.getNuggetItemStack(9)));
		});
	}
	
	private static void furnace() {
		// Resources
		ResourceUtil.iterate(resource -> {
			// Ore -> Ingot
			registerSmelting(resource.getOreItemStack(), resource.getIngotItemStack(), 0.75F);
			// Dust -> Ingot
			registerSmelting(resource.getDustItemStack(), resource.getIngotItemStack(), 0.5F);
		});
	}
	
	private static void registerCrafting(String name, IRecipe recipe) {
		ForgeRegistries.RECIPES.register(recipe.setRegistryName(new ResourceLocation(UConstants.MODID, name)));
	}
	
}
