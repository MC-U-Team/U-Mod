package info.u_team.u_mod.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class MachineRecipe implements IRecipe<IInventory> {
	
	protected final ResourceLocation location;
	protected final IRecipeType<?> type;
	protected final IRecipeSerializer<?> serializer;
	protected final int totalTime;
	protected final int consumptionOnStart;
	protected final int consumptionPerTick;
	
	public MachineRecipe(ResourceLocation location, IRecipeType<?> type, IRecipeSerializer<?> serializer, int totalTime, int consumptionOnStart, int consumptionPerTick) {
		this.location = location;
		this.type = type;
		this.serializer = serializer;
		this.totalTime = totalTime;
		this.consumptionOnStart = consumptionOnStart;
		this.consumptionPerTick = consumptionPerTick;
	}
	
	@Override
	public abstract boolean matches(IInventory inventory, World world);
	
	@Override
	public abstract NonNullList<Ingredient> getIngredients();
	
	public abstract NonNullList<ItemStack> getPossibleOutputs();
	
	public NonNullList<ItemStack> getPossibleOutputs(IInventory inventory) {
		return getPossibleOutputs();
	}
	
	public abstract NonNullList<ItemStack> getOutputs();
	
	public NonNullList<ItemStack> getOutputs(IInventory inventory) {
		return getOutputs();
	}
	
	// Getter
	
	public int getTotalTime() {
		return totalTime;
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
