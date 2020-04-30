package info.u_team.u_mod.recipe;

import java.util.*;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;

public class ShapelessMachineRecipe extends MachineRecipe {
	
	private final NonNullList<Ingredient> ingredients;
	private final NonNullList<ItemStack> outputs;
	private final boolean isSimple;
	
	public ShapelessMachineRecipe(ResourceLocation location, IRecipeType<?> type, IRecipeSerializer<?> serializer, NonNullList<Ingredient> ingredients, NonNullList<ItemStack> outputs, int totalTime, int consumptionOnStart, int consumptionPerTick) {
		super(location, type, serializer, totalTime, consumptionOnStart, consumptionPerTick);
		this.ingredients = ingredients;
		this.outputs = outputs;
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
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}
	
	@Override
	public NonNullList<ItemStack> getPossibleOutputs() {
		return outputs; // TODO Add possible outputs here
	}
	
	@Override
	public NonNullList<ItemStack> getOutputs() {
		return outputs;
	}
}