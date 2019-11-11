package info.u_team.u_mod.recipe;

import java.util.*;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;

public class MachineRecipe implements IRecipe<IInventory> {
	
	private final ResourceLocation location;
	private final IRecipeType<?> type;
	private final IRecipeSerializer<?> serializer;
	private final NonNullList<Ingredient> ingredients;
	private final NonNullList<ItemStack> outputs;
	private final int time;
	private final int consumptionOnStart;
	private final int consumptionPerTick;
	private final boolean isSimple;
	
	public MachineRecipe(ResourceLocation location, IRecipeType<?> type, IRecipeSerializer<?> serializer, NonNullList<Ingredient> ingredients, NonNullList<ItemStack> outputs, int time, int consumptionOnStart, int consumptionPerTick) {
		this.location = location;
		this.type = type;
		this.serializer = serializer;
		this.ingredients = ingredients;
		this.outputs = outputs;
		this.time = time;
		this.consumptionOnStart = consumptionOnStart;
		this.consumptionPerTick = consumptionPerTick;
		this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
	}
	
	@Override
	public boolean matches(IInventory inventory, World world) {
		final RecipeItemHelper helper = new RecipeItemHelper();
		final List<ItemStack> inputs = new ArrayList<>();
		int validInputCount = 0;
		
		for (int index = 0; index < inventory.getSizeInventory(); ++index) {
			final ItemStack stack = inventory.getStackInSlot(index);
			if (!stack.isEmpty()) {
				++validInputCount;
				if (isSimple) {
					helper.func_221264_a(stack, 1);
				} else {
					inputs.add(stack);
				}
			}
		}
		return validInputCount == ingredients.size() && (isSimple ? helper.canCraft(this, null) : RecipeMatcher.findMatches(inputs, ingredients) != null);
	}
	
	public NonNullList<ItemStack> getOutput(IInventory inventory) {
		return outputs;
	}
	
	// Getter
	
	public int getTime() {
		return time;
	}
	
	public int getConsumptionOnStart() {
		return consumptionOnStart;
	}
	
	public int getConsumptionPerTick() {
		return consumptionPerTick;
	}
	
	// Basic stuff
	
	@Override
	public ResourceLocation getId() {
		return location;
	}
	
	@Override
	public IRecipeType<?> getType() {
		return type;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return serializer;
	}
	
	/**
	 * Is only needed for crafting recipes
	 */
	@Override
	public boolean canFit(int width, int height) {
		return true;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}
	
	// Unused
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack getCraftingResult(IInventory inventory) {
		return ItemStack.EMPTY;
	}
	
}
