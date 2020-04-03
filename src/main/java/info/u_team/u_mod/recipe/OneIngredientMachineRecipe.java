package info.u_team.u_mod.recipe;

import java.util.function.Function;

import com.google.gson.JsonObject;

import info.u_team.u_mod.util.inventory.SerializeUtil;
import info.u_team.u_team_core.recipeserializer.URecipeSerializer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class OneIngredientMachineRecipe extends MachineRecipe {
	
	protected final NonNullList<Ingredient> ingredients;
	protected final NonNullList<ItemStack> outputs;
	
	public OneIngredientMachineRecipe(ResourceLocation location, IRecipeType<?> type, IRecipeSerializer<?> serializer, Ingredient ingredient, ItemStack output, int totalTime, int consumptionOnStart, int consumptionPerTick) {
		super(location, type, serializer, totalTime, consumptionOnStart, consumptionPerTick);
		ingredients = NonNullList.from(Ingredient.EMPTY, ingredient);
		outputs = NonNullList.from(ItemStack.EMPTY, output);
	}
	
	@Override
	public boolean matches(IInventory inventory, World world) {
		return ingredients.get(0).test(inventory.getStackInSlot(0));
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}
	
	@Override
	public NonNullList<ItemStack> getOutputs() {
		return outputs;
	}
	
	@Override
	public NonNullList<ItemStack> getOutputs(IInventory inventory) {
		return outputs;
	}
	
	public static class Serializer<T extends OneIngredientMachineRecipe> extends URecipeSerializer<T> {
		
		private final IFactory<T> factory;
		
		@SuppressWarnings("unchecked")
		public Serializer(String name, IRecipeType<T> type) {
			this(name, serializer -> (location, ingredient, output, totalTime, consumptionOnStart, consumptionPerTick) -> (T) new OneIngredientMachineRecipe(location, type, serializer, ingredient, output, totalTime, consumptionOnStart, consumptionPerTick));
		}
		
		public Serializer(String name, Function<Serializer<T>, IFactory<T>> function) {
			super(name);
			factory = function.apply(this);
		}
		
		@Override
		public T read(ResourceLocation location, JsonObject json) {
			final Ingredient ingredient = SerializeUtil.deserializeIngredient(json.get("ingredient"));
			final ItemStack output = SerializeUtil.deserializeItemStack(json.get("output"));
			final int totalTime = JSONUtils.getInt(json, "total_time", 200);
			final int consumptionOnStart = JSONUtils.getInt(json, "consumption_on_start", 0);
			final int consumptionPerTick = JSONUtils.getInt(json, "consumption_per_tick", 5);
			return factory.create(location, ingredient, output, totalTime, consumptionOnStart, consumptionPerTick);
		}
		
		@Override
		public T read(ResourceLocation location, PacketBuffer buffer) {
			final Ingredient ingredient = Ingredient.read(buffer);
			final ItemStack output = buffer.readItemStack();
			final int totalTime = buffer.readInt();
			final int consumptionOnStart = buffer.readInt();
			final int consumptionPerTick = buffer.readInt();
			return factory.create(location, ingredient, output, totalTime, consumptionOnStart, consumptionPerTick);
		}
		
		@Override
		public void write(PacketBuffer buffer, T recipe) {
			recipe.ingredients.get(0).write(buffer);
			buffer.writeItemStack(recipe.outputs.get(0));
			buffer.writeInt(recipe.totalTime);
			buffer.writeInt(recipe.consumptionOnStart);
			buffer.writeInt(recipe.consumptionPerTick);
		}
		
		public static interface IFactory<T extends OneIngredientMachineRecipe> {
			
			T create(ResourceLocation location, Ingredient ingredient, ItemStack output, int totalTime, int consumptionOnStart, int consumptionPerTick);
		}
	}
	
}
