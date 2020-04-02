package info.u_team.u_mod.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class OneIngredientMachineRecipe extends MachineRecipe {
	
	private final NonNullList<Ingredient> ingredients;
	private final NonNullList<ItemStack> outputs;
	
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
	public NonNullList<ItemStack> getOutputs(IInventory inventory) {
		return outputs;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}
	
}
