package info.u_team.u_mod.data.builder;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe.Serializer;
import info.u_team.u_mod.util.inventory.SerializeUtil;
import net.minecraft.advancements.*;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;

public class OneIngredientMachineRecipeBuilder {
	
	private final OneIngredientMachineRecipe.Serializer<?> serializer;
	
	private final Ingredient ingredient;
	private final ItemStack output;
	private final int totalTime;
	private int consumptionOnStart = 0;
	private int consumptionPerTick = 10;
	
	private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
	
	public static OneIngredientMachineRecipeBuilder machineRecipe(Serializer<?> serializer, Ingredient ingredient, IItemProvider output, int totalTime) {
		return machineRecipe(serializer, ingredient, new ItemStack(output), totalTime);
	}
	
	public static OneIngredientMachineRecipeBuilder machineRecipe(Serializer<?> serializer, Ingredient ingredient, ItemStack output, int totalTime) {
		return new OneIngredientMachineRecipeBuilder(serializer, ingredient, output, totalTime);
	}
	
	public OneIngredientMachineRecipeBuilder(Serializer<?> serializer, Ingredient ingredient, ItemStack output, int totalTime) {
		this.serializer = serializer;
		this.ingredient = ingredient;
		this.output = output;
		this.totalTime = totalTime;
	}
	
	public OneIngredientMachineRecipeBuilder setConsumtionOnStart(int consumptionOnStart) {
		this.consumptionOnStart = consumptionOnStart;
		return this;
	}
	
	public OneIngredientMachineRecipeBuilder setConsumtionPerTick(int consumptionPerTick) {
		this.consumptionPerTick = consumptionPerTick;
		return this;
	}
	
	public OneIngredientMachineRecipeBuilder addCriterion(String name, ICriterionInstance criterion) {
		advancementBuilder.withCriterion(name, criterion);
		return this;
	}
	
	public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
		validate(location);
		advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(location)).withRewards(AdvancementRewards.Builder.recipe(location)).withRequirementsStrategy(IRequirementsStrategy.OR);
		consumer.accept(new Result(serializer, location, ingredient, output, totalTime, consumptionOnStart, consumptionPerTick, advancementBuilder, new ResourceLocation(location.getNamespace(), "recipes/" + location.getPath())));
	}
	
	private void validate(ResourceLocation id) {
		if (advancementBuilder.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
	
	public static class Result implements IFinishedRecipe {
		
		private final OneIngredientMachineRecipe.Serializer<?> serializer;
		
		private final ResourceLocation location;
		
		private final Ingredient ingredient;
		private final ItemStack output;
		private final int totalTime;
		private final int consumptionOnStart;
		private final int consumptionPerTick;
		private final Advancement.Builder advancementBuilder;
		private final ResourceLocation advancementId;
		
		public Result(Serializer<?> serializer, ResourceLocation location, Ingredient ingredient, ItemStack output, int totalTime, int consumptionOnStart, int consumptionPerTick, Builder advancementBuilder, ResourceLocation advancementId) {
			this.serializer = serializer;
			this.location = location;
			this.ingredient = ingredient;
			this.output = output;
			this.totalTime = totalTime;
			this.consumptionOnStart = consumptionOnStart;
			this.consumptionPerTick = consumptionPerTick;
			this.advancementBuilder = advancementBuilder;
			this.advancementId = advancementId;
		}
		
		@Override
		public void serialize(JsonObject json) {
			json.add("ingredient", SerializeUtil.serializeIngredient(ingredient));
			json.add("output", SerializeUtil.serializeItemStack(output));
			json.addProperty("total_time", totalTime);
			json.addProperty("consumption_on_start", consumptionOnStart);
			json.addProperty("consumption_per_tick", consumptionPerTick);
		}
		
		@Override
		public IRecipeSerializer<?> getSerializer() {
			return serializer;
		}
		
		@Override
		public ResourceLocation getID() {
			return location;
		}
		
		@Override
		public JsonObject getAdvancementJson() {
			return advancementBuilder.serialize();
		}
		
		@Override
		public ResourceLocation getAdvancementID() {
			return advancementId;
		}
	}
}
