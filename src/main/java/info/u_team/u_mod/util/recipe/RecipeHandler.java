package info.u_team.u_mod.util.recipe;

import java.util.Optional;
import java.util.function.BiFunction;

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
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class RecipeHandler<T extends IRecipe<IInventory>> implements INBTSerializable<CompoundNBT> {
	
	private final LazyOptional<BasicEnergyStorage> energyOptional;
	private final LazyOptional<UItemStackHandler> ingredientSlotsOptional;
	private final LazyOptional<UItemStackHandler> outputSlotsOptional;
	private final LazyOptional<UItemStackHandler> upgradeSlotsOptional;
	
	private final RecipeData<T> recipeData;
	
	private final RecipeCache<T> recipeCache;
	
	private final Runnable dirtyMarker;
	
	private BiFunction<T, Integer, Integer> totalTimeModifier = (recipe, totalTime) -> totalTime;
	
	private int totalTime;
	private int time;
	
	private final BufferReferenceHolder percentTracker = ExtendedBufferReferenceHolder.createFloatHolder(() -> time / (float) totalTime, value -> percent = value);
	
	// Client only value
	private float percent;
	
	public RecipeHandler(IRecipeType<T> recipeType, LazyOptional<BasicEnergyStorage> energyOptional, int ingredientSize, LazyOptional<UItemStackHandler> ingredientSlotsOptional, LazyOptional<UItemStackHandler> outputSlotsOptional, LazyOptional<UItemStackHandler> upgradeSlotsOptional, RecipeData<T> recipeData, Runnable dirtyMarker) {
		this.energyOptional = energyOptional;
		this.ingredientSlotsOptional = ingredientSlotsOptional;
		this.outputSlotsOptional = outputSlotsOptional;
		this.upgradeSlotsOptional = upgradeSlotsOptional;
		
		this.recipeData = recipeData;
		this.dirtyMarker = dirtyMarker;
		recipeCache = new RecipeCache<>(recipeType, ingredientSize);
	}
	
	public void update(World world) {
		// If one lazy optional is not present we will fail. We will not include the upgrade slots here as they are not
		// necessary to process items
		if (!energyOptional.isPresent() || !ingredientSlotsOptional.isPresent() || !outputSlotsOptional.isPresent()) {
			resetTimeAndMarkDirty();
			return;
		}
		
		final BasicEnergyStorage energy = energyOptional.orElseThrow(AssertionError::new);
		final RecipeWrapper recipeWrapper = new RecipeWrapper(ingredientSlotsOptional.orElseThrow(AssertionError::new));
		final UItemStackHandler outputSlots = outputSlotsOptional.orElseThrow(AssertionError::new);
		
		// If input slots are empty there could be no recipe
		if (recipeWrapper.isEmpty()) {
			resetTimeAndMarkDirty();
			return;
		}
		
		// Recipe optional
		final Optional<T> recipeOptional = recipeCache.getRecipe(world, recipeWrapper);
		
		// If no recipe was found we cannot proceed
		if (!recipeOptional.isPresent()) {
			resetTimeAndMarkDirty();
			return;
		}
		
		// Get recipe
		final T recipe = recipeOptional.get();
		
		// Set the total time to the total time from the recipe (trough the function for modifiers)
		totalTime = totalTimeModifier.apply(recipe, recipeData.getTotalTime(recipe));
		
		if (time == 0) {
			// Check if we can process (e.g. output slot is not full)
			if (!canProcess(recipe, recipeWrapper, outputSlots)) {
				return;
			}
			
			// If we have no energy for the consumption at the start we cannot proceed
			if (!doConsumtionOnStart(recipe, energy)) {
				return;
			}
			
			// Mark the consumption on start dirty
			dirtyMarker.run();
		}
		
		// If we have not energy for the consumption every tick we cannot proceed. We will not reset the timer here.
		if (!doConsumtionPerTick(recipe, energy)) {
			return;
		}
		
		// Increase the processing time by one
		time++;
		
		// If the time is equal to the total time needed we can process
		if (time >= totalTime) {
			time = 0;
			process(recipe, recipeWrapper, outputSlots);
		}
		
		// Mark the consumption per tick and the time dirty
		dirtyMarker.run();
	}
	
	private void resetTimeAndMarkDirty() {
		if (time != 0) {
			time = 0;
			dirtyMarker.run();
		}
	}
	
	protected boolean doConsumtionOnStart(T recipe, BasicEnergyStorage energyStorage) {
		final int consumtion = recipeData.getConsumptionOnStart(recipe);
		if (energyStorage.getEnergy() >= consumtion) {
			energyStorage.addEnergy(-consumtion);
			return true;
		}
		return false;
	}
	
	protected boolean doConsumtionPerTick(T recipe, BasicEnergyStorage energyStorage) {
		final int consumtion = recipeData.getConsumptionPerTick(recipe);
		if (energyStorage.getEnergy() >= consumtion) {
			energyStorage.addEnergy(-consumtion);
			return true;
		}
		return false;
	}
	
	protected boolean canProcess(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler) {
		final NonNullList<ItemStack> recipeOutputs = recipeData.getPossibleRecipeOutputs(recipe, recipeWrapper);
		for (int index = 0; index < recipeOutputs.size(); index++) {
			final ItemStack recipeOutput = recipeOutputs.get(index);
			if (!ItemHandlerHelper.insertItemStacked(outputHandler, recipeOutput.copy(), true).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	protected void process(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler) {
		final NonNullList<ItemStack> recipeOutputs = recipeData.getRecipeOutputs(recipe, recipeWrapper);
		// Add to output
		for (int index = 0; index < recipeOutputs.size(); index++) {
			final ItemStack recipeOutput = recipeOutputs.get(index);
			if (!recipeOutput.isEmpty()) {
				ItemHandlerHelper.insertItemStacked(outputHandler, recipeOutput.copy(), false);
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
		compound.putInt("time", time);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		time = compound.getInt("time");
	}
	
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeFloat(time / (float) totalTime);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		percent = buffer.readFloat();
	}
	
	public BufferReferenceHolder getPercentTracker() {
		return percentTracker;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float getPercent() {
		return percent;
	}
	
	// Setter
	public void setTotalTimeModifier(BiFunction<T, Integer, Integer> totalTimeModifier) {
		this.totalTimeModifier = totalTimeModifier;
	}
	
}
