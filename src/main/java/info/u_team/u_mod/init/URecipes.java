package info.u_team.u_mod.init;

import static info.u_team.u_team_core.registry.CommonRegistry.registerSmelting;
import static net.minecraftforge.fml.common.registry.ForgeRegistries.RECIPES;

import org.apache.commons.lang3.StringUtils;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.resource.*;
import info.u_team.u_team_core.util.NonNullListUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.oredict.OreIngredient;

public class URecipes {
	
	public static void init() {
		furnace();
		crafting();
	}
	
	/*
	 * TODO make methods and make it clearer. Just messy now but still better than
	 * 26*4 json files for crafting
	 */
	private static void crafting() {
		// Resources
//		for (EnumResources1 resources : EnumResources1.VALUES) {
//			int metadata = resources.getMetadata();
//			String name = StringUtils.capitalize(resources.getName());
//			
//			// Ingot -> Block
//			NonNullList<Ingredient> list1 = NonNullListUtil.create();
//			for (int i = 0; i < 9; i++) {
//				list1.add(new OreIngredient("ingot" + name));
//			}
//			ShapelessRecipes recipe1 = new ShapelessRecipes("", new ItemStack(UBlocks.resource_block1, 1, metadata), list1);
//			recipe1.setRegistryName(new ResourceLocation(UConstants.MODID, "ingot_block_" + resources.getName()));
//			RECIPES.register(recipe1);
//			
//			// Block -> Ingot
//			NonNullList<Ingredient> list2 = NonNullListUtil.create();
//			list2.add(new OreIngredient("block" + name));
//			ShapelessRecipes recipe2 = new ShapelessRecipes("", new ItemStack(UItems.resource_ingot1, 9, metadata), list2);
//			recipe2.setRegistryName(new ResourceLocation(UConstants.MODID, "block_ingot_" + resources.getName()));
//			RECIPES.register(recipe2);
//			
//			// Ingot -> Nugget
//			NonNullList<Ingredient> list3 = NonNullListUtil.create();
//			list3.add(new OreIngredient("ingot" + name));
//			ShapelessRecipes recipe3 = new ShapelessRecipes("", new ItemStack(UItems.resource_nugget1, 9, metadata), list3);
//			recipe3.setRegistryName(new ResourceLocation(UConstants.MODID, "ingot_nugget_" + resources.getName()));
//			RECIPES.register(recipe3);
//			
//			// Nugget -> Ingot
//			NonNullList<Ingredient> list4 = NonNullListUtil.create();
//			for (int i = 0; i < 9; i++) {
//				list4.add(new OreIngredient("nugget" + name));
//			}
//			ShapelessRecipes recipe4 = new ShapelessRecipes("", new ItemStack(UItems.resource_ingot1, 1, metadata), list4);
//			recipe4.setRegistryName(new ResourceLocation(UConstants.MODID, "nugget_ingot_" + resources.getName()));
//			RECIPES.register(recipe4);
//		}
//		
//		for (EnumResources2 resources : EnumResources2.VALUES) {
//			int metadata = resources.getMetadata();
//			String name = StringUtils.capitalize(resources.getName());
//			
//			// Ingot -> Block
//			NonNullList<Ingredient> list1 = NonNullListUtil.create();
//			for (int i = 0; i < 9; i++) {
//				list1.add(new OreIngredient("ingot" + name));
//			}
//			ShapelessRecipes recipe1 = new ShapelessRecipes("", new ItemStack(UBlocks.resource_block2, 1, metadata), list1);
//			recipe1.setRegistryName(new ResourceLocation(UConstants.MODID, "ingot_block_" + resources.getName()));
//			RECIPES.register(recipe1);
//			
//			// Block -> Ingot
//			NonNullList<Ingredient> list2 = NonNullListUtil.create();
//			list2.add(new OreIngredient("block" + name));
//			ShapelessRecipes recipe2 = new ShapelessRecipes("", new ItemStack(UItems.resource_ingot2, 9, metadata), list2);
//			recipe2.setRegistryName(new ResourceLocation(UConstants.MODID, "block_ingot_" + resources.getName()));
//			RECIPES.register(recipe2);
//			
//			// Ingot -> Nugget
//			NonNullList<Ingredient> list3 = NonNullListUtil.create();
//			list3.add(new OreIngredient("ingot" + name));
//			ShapelessRecipes recipe3 = new ShapelessRecipes("", new ItemStack(UItems.resource_nugget2, 9, metadata), list3);
//			recipe3.setRegistryName(new ResourceLocation(UConstants.MODID, "ingot_nugget_" + resources.getName()));
//			RECIPES.register(recipe3);
//			
//			// Nugget -> Ingot
//			NonNullList<Ingredient> list4 = NonNullListUtil.create();
//			for (int i = 0; i < 9; i++) {
//				list4.add(new OreIngredient("nugget" + name));
//			}
//			ShapelessRecipes recipe4 = new ShapelessRecipes("", new ItemStack(UItems.resource_ingot2, 1, metadata), list4);
//			recipe4.setRegistryName(new ResourceLocation(UConstants.MODID, "nugget_ingot_" + resources.getName()));
//			RECIPES.register(recipe4);
//		}
	}
	
	private static void furnace() {
		// Resources
		ResourceUtil.iterate(resource -> {
			registerSmelting(resource.getOreItemStack(), resource.getIngotItemStack(), 0.75F);
			registerSmelting(resource.getDustItemStack(), resource.getIngotItemStack(), 0.75F);
		});
	}
	
}
