package info.u_team.u_mod.util.recipe;

import java.util.Optional;

import info.u_team.u_mod.util.ExtendedBufferReferenceHolder;
import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.inventory.UItemStackHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class RecipeHandler<T extends IRecipe<IInventory>> implements INBTSerializable<CompoundNBT> {
	
	private final LazyOptional<UItemStackHandler> ingredientSlots;
	private final LazyOptional<UItemStackHandler> outputSlots;
	private final LazyOptional<BasicEnergyStorage> energy;
	
	private final RecipeData<T> recipeData;
	
	private final RecipeCache<T> recipeCache;
	
	private int totalTime;
	private int time;
	
	private final BufferReferenceHolder percentTracker = ExtendedBufferReferenceHolder.createFloatHolder(() -> time / (float) totalTime, value -> percent = value);
	
	// Client only value
	private float percent;
	
	public RecipeHandler(IRecipeType<T> recipeType, LazyOptional<BasicEnergyStorage> energy, LazyOptional<UItemStackHandler> ingredientSlots, LazyOptional<UItemStackHandler> outputSlots, RecipeData<T> recipeData) {
		this.energy = energy;
		this.ingredientSlots = ingredientSlots;
		this.outputSlots = outputSlots;
		this.recipeData = recipeData;
		recipeCache = new RecipeCache<>(recipeType, ingredientSlots.orElseThrow(IllegalStateException::new).getSlots());
	}
	
	public void update(World world) {
		// If some lazy optional is invalid we cannot proceed
		if (!ingredientSlots.isPresent() || !outputSlots.isPresent() || !energy.isPresent()) {
			time = 0;
			return;
		}
		
		// Provided slots and energy
		final RecipeWrapper recipeWrapper = new RecipeWrapper(ingredientSlots.orElseThrow(IllegalStateException::new));
		final UItemStackHandler outputHandler = outputSlots.orElseThrow(IllegalStateException::new);
		final BasicEnergyStorage energyStorage = energy.orElseThrow(IllegalStateException::new);
		
		// Recipe optional
		final Optional<T> recipeOptional = recipeCache.getRecipe(world, recipeWrapper);
		
		// If no recipe was found we cannot proceed
		if (!recipeOptional.isPresent()) {
			time = 0;
			return;
		}
		
		// Get recipe
		final T recipe = recipeOptional.get();
		// Set the total time to the total time from the recipe
		totalTime = recipeData.getTotalTime(recipe);
		
		// Check if the recipe is valid when the timer starts
		if (time == 0) {
			if (!isRecipeValid(recipe, recipeWrapper)) {
				time = 0;
				return;
			}
		}
		
		// Check if we can process (e.g. output slot is not full)
		if (!canProcess(recipe, recipeWrapper, outputHandler)) {
			time = 0;
			return;
		}
		
		// If we have no energy for the consumption at the start we cannot proceed
		if (time == 0) {
			if (!doConsumtionOnStart(recipe, energyStorage)) {
				time = 0;
				return;
			}
		}
		
		// If we have not energy for the consumption every tick we cannot proceed. We will not reset the timer here.
		if (!doConsumtionPerTick(recipe, energyStorage)) {
			return;
		}
		
		// Increase the processing time by one
		time++;
		
		// If the time is equal to the total time needed we can process
		if (time == totalTime) {
			time = 0;
			process(recipe, recipeWrapper, outputHandler);
		}
		return;
	}
	
	protected boolean doConsumtionPerTick(T recipe, BasicEnergyStorage energyStorage) {
		final int consumtion = recipeData.getConsumptionPerTick(recipe);
		if (energyStorage.getEnergy() >= consumtion) {
			energyStorage.addEnergy(-consumtion);
			return true;
		}
		return false;
	}
	
	protected boolean doConsumtionOnStart(T recipe, BasicEnergyStorage energyStorage) {
		final int consumtion = recipeData.getConsumptionOnStart(recipe);
		if (energyStorage.getEnergy() >= consumtion) {
			energyStorage.addEnergy(-consumtion);
			return true;
		}
		return false;
	}
	
	protected boolean isRecipeValid(T recipe, RecipeWrapper recipeWrapper) {
		final NonNullList<ItemStack> recipeOutputs = recipeData.getRecipeOutputs(recipe, recipeWrapper);
		return !recipeOutputs.isEmpty() && !recipeOutputs.stream().allMatch(ItemStack::isEmpty);
	}
	
	protected boolean canProcess(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler) {
		final NonNullList<ItemStack> recipeOutputs = recipeData.getRecipeOutputs(recipe, recipeWrapper);
		for (int index = 0; index < recipeOutputs.size(); index++) {
			final ItemStack recipeOutput = recipeOutputs.get(index);
			final ItemStack slotOutput = outputHandler.getStackInSlot(index);
			
			// Logic copied from furnace
			if (slotOutput.isEmpty()) {
				continue;
			} else if (!slotOutput.isItemEqual(recipeOutput)) {
				return false;
			} else if (slotOutput.getCount() + recipeOutput.getCount() <= outputHandler.getSlotLimit(index) && slotOutput.getCount() + recipeOutput.getCount() <= slotOutput.getMaxStackSize()) {
				continue;
			} else {
				if (slotOutput.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxStackSize()) {
					continue;
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	protected void process(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler) {
		final NonNullList<ItemStack> recipeOutputs = recipeData.getRecipeOutputs(recipe, recipeWrapper);
		// Add to output
		for (int index = 0; index < recipeOutputs.size(); index++) {
			final ItemStack recipeOutput = recipeOutputs.get(index);
			// If recipe output is empty we continue the loop
			if (recipeOutput.isEmpty()) {
				continue;
			}
			final ItemStack slotOutput = outputHandler.getStackInSlot(index);
			if (slotOutput.isEmpty()) {
				outputHandler.setStackInSlot(0, recipeOutput.copy());
			} else if (slotOutput.getItem() == recipeOutput.getItem()) {
				slotOutput.grow(recipeOutput.getCount());
			}
		}
		// Remove from ingredient
		for (int index = 0; index < recipeWrapper.getSizeInventory(); index++) {
			final ItemStack ingredientStack = recipeWrapper.getStackInSlot(index);
			ingredientStack.shrink(1); // We must make this dynamic... Custom ingredient or other stuff...
		}
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		final CompoundNBT compound = new CompoundNBT();
		ingredientSlots.ifPresent(handler -> compound.put("ingredient", handler.serializeNBT()));
		outputSlots.ifPresent(handler -> compound.put("output", handler.serializeNBT()));
		compound.putInt("time", time);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		ingredientSlots.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("ingredient")));
		outputSlots.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("output")));
		time = compound.getInt("time");
	}
	
	public void invalidate() {
		ingredientSlots.invalidate();
		outputSlots.invalidate();
	}
	
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeFloat(time / (float) totalTime);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		percent = buffer.readFloat();
	}
	
	public LazyOptional<BasicEnergyStorage> getEnergy() {
		return energy;
	}
	
	public LazyOptional<UItemStackHandler> getIngredient() {
		return ingredientSlots;
	}
	
	public LazyOptional<UItemStackHandler> getOutput() {
		return outputSlots;
	}
	
	public BufferReferenceHolder getPercentTracker() {
		return percentTracker;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float getPercent() {
		return percent;
	}
	
}
