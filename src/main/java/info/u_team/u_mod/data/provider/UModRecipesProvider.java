package info.u_team.u_mod.data.provider;

import static info.u_team.u_mod.init.UModRecipeSerializers.CRUSHER;
import static info.u_team.useful_resources.api.type.BlockResourceType.*;
import static info.u_team.useful_resources.api.type.ItemResourceType.*;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import info.u_team.u_mod.data.builder.OneIngredientMachineRecipeBuilder;
import info.u_team.u_team_core.data.*;
import info.u_team.u_team_core.util.TagUtil;
import info.u_team.useful_resources.api.ResourceRegistry;
import info.u_team.useful_resources.api.registry.RegistryEntry;
import info.u_team.useful_resources.api.resource.IResource;
import info.u_team.useful_resources.api.type.*;
import net.minecraft.block.Block;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class UModRecipesProvider extends CommonRecipesProvider {
	
	public UModRecipesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		// Add resource recipes
		
		ResourceRegistry.getResources().forEach(resource -> {
			final Map<BlockResourceType, RegistryEntry<Block>> blocks = resource.getBlocks();
			final Map<ItemResourceType, RegistryEntry<Item>> items = resource.getItems();
			
			// ORE -> CRUSHED ORE
			if (shouldAddRecipe(resource, ORE, CRUSHED_ORE)) {
				final Tag<Item> oreTag = TagUtil.fromBlockTag(ORE.getTag(resource));
				final Item crushedItem = items.get(CRUSHED_ORE).get();
				
				OneIngredientMachineRecipeBuilder.machineRecipe(CRUSHER.get(), Ingredient.fromTag(oreTag), crushedItem, 2, 100) //
						.addCriterion("has_ore", hasItem(oreTag)) //
						.build(consumer, createLocationForResource(resource, "crusher/crushed_ore_from_stone_ore"));
			}
			
			// NETHER_ORE -> CRUSHED NETHER_ORE
			if (shouldAddRecipe(resource, NETHER_ORE, CRUSHED_NETHER_ORE)) {
				final Tag<Item> oreTag = TagUtil.fromBlockTag(NETHER_ORE.getTag(resource));
				final Item crushedItem = items.get(CRUSHED_NETHER_ORE).get();
				
				OneIngredientMachineRecipeBuilder.machineRecipe(CRUSHER.get(), Ingredient.fromTag(oreTag), crushedItem, 2, 100) //
						.addCriterion("has_nether_ore", hasItem(oreTag)) //
						.build(consumer, createLocationForResource(resource, "crusher/crushed_nether_ore_from_nether_ore"));
			}
		});
	}
	
	private ResourceLocation createLocationForResource(IResource resource, String name) {
		return new ResourceLocation(modid, resource.getName() + "/" + name);
	}
	
	private boolean shouldAddRecipe(IResource resource, IResourceType<?>... types) {
		final boolean allTypes = Stream.of(types).allMatch(type -> {
			if (type instanceof BlockResourceType) {
				return resource.getBlocks().containsKey(type);
			} else if (type instanceof FluidResourceType) {
				return resource.getFluids().containsKey(type);
			} else if (type instanceof ItemResourceType) {
				return resource.getItems().containsKey(type);
			}
			return false;
		});
		final boolean anyRegistered = Stream.of(types).anyMatch(type -> {
			if (type instanceof BlockResourceType) {
				return resource.getRegistryBlocks().contains(resource.getBlocks().get(type));
			} else if (type instanceof FluidResourceType) {
				return resource.getRegistryFluids().contains(resource.getFluids().get(type));
			} else if (type instanceof ItemResourceType) {
				return resource.getRegistryItems().contains(resource.getItems().get(type));
			}
			return false;
		});
		return allTypes && anyRegistered;
	}
	
}
