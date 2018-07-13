package info.u_team.u_mod.init;

import static info.u_team.u_team_core.registry.CommonRegistry.registerSmelting;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.recipe.RecipeShapelessOre;
import info.u_team.u_mod.resource.ResourceUtil;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class URecipes {
	
	public static void init() {
		furnace();
		crafting();
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
