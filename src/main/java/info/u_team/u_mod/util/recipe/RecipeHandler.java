package info.u_team.u_mod.util.recipe;

import java.util.Optional;
import java.util.function.BiFunction;

import info.u_team.u_mod.util.*;
import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.inventory.UItemStackHandler;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
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
	private final LazyOptional<UItemStackHandler> itemIngredientSlotsOptional;
	private final LazyOptional<UItemStackHandler> itemOutputSlotsOptional;
	private final LazyOptional<UItemStackHandler> upgradeSlotsOptional;
	
	private final RecipeData<T> recipeData;
	private final RecipeCache<T> recipeCache;
	
	private final Runnable dirtyMarker;
	
	private final BooleanConsumer workingCallback;
	
	private BiFunction<T, Integer, Integer> totalTimeModifier = (recipe, totalTime) -> totalTime;
	
	private int totalTime;
	private int time;
	
	private final BufferReferenceHolder percentTracker = ExtendedBufferReferenceHolder.createFloatHolder(() -> MathUtil.valueInRange(0, 1, time / (float) totalTime), value -> percent = value);
	
	// Client only value
	private float percent;
	
	public RecipeHandler(IRecipeType<T> recipeType, LazyOptional<BasicEnergyStorage> energyOptional, int ingredientSize, LazyOptional<UItemStackHandler> itemIngredientSlotsOptional, LazyOptional<UItemStackHandler> itemOutputSlotsOptional, LazyOptional<UItemStackHandler> upgradeSlotsOptional, RecipeData<T> recipeData, Runnable dirtyMarker, BooleanConsumer workingCallback) {
		this.energyOptional = energyOptional;
		this.itemIngredientSlotsOptional = itemIngredientSlotsOptional;
		this.itemOutputSlotsOptional = itemOutputSlotsOptional;
		this.upgradeSlotsOptional = upgradeSlotsOptional;
		
		this.recipeData = recipeData;
		this.dirtyMarker = dirtyMarker;
		this.workingCallback = workingCallback;
		
		recipeCache = new RecipeCache<>(recipeType, ingredientSize);
	}
	
	public void update(World world) {
		// If one lazy optional is not present we will fail. We will not include the upgrade slots here as they are not
		// necessary to process items
		if (!energyOptional.isPresent() || !itemIngredientSlotsOptional.isPresent() || !itemOutputSlotsOptional.isPresent()) {
			time = 0;
			workingCallback.accept(false);
			return;
		}
		
		final BasicEnergyStorage energy = energyOptional.orElseThrow(AssertionError::new);
		final RecipeWrapper recipeWrapper = new RecipeWrapper(itemIngredientSlotsOptional.orElseThrow(AssertionError::new));
		final UItemStackHandler outputSlots = itemOutputSlotsOptional.orElseThrow(AssertionError::new);
		
		// If input slots are empty there could be no recipe
		if (recipeWrapper.isEmpty()) {
			time = 0;
			workingCallback.accept(false);
			return;
		}
		
		// Recipe optional
		final Optional<T> recipeOptional = recipeCache.getRecipe(world, recipeWrapper);
		
		// If no recipe was found we cannot proceed
		if (!recipeOptional.isPresent()) {
			time = 0;
			workingCallback.accept(false);
			return;
		}
		
		// Get recipe
		final T recipe = recipeOptional.get();
		
		if (canRun(recipe, recipeWrapper, outputSlots, energy)) {
			// Set the total time to the total time from the recipe (trough the function for modifiers)
			totalTime = totalTimeModifier.apply(recipe, recipeData.getTotalTime(recipe));
			
			if (time == 0) {
				energy.removeEnergy(recipeData.getConsumptionOnStart(recipe)); // Remove energy for start
			}
			
			energy.removeEnergy(recipeData.getConsumptionPerTick(recipe)); // Remove energy per tick
			
			// Increase the processing time by one
			time++;
			
			// If the time is equal to the total time needed we can process
			if (time >= totalTime) {
				time = 0;
				process(recipe, recipeWrapper, outputSlots);
			}
			
			// Mark the consumption per tick and the time dirty
			dirtyMarker.run();
			
			workingCallback.accept(true);
		} else {
			workingCallback.accept(false);
		}
	}
	
	protected boolean canRun(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler, BasicEnergyStorage energyStorage) {
		boolean isRecipeReadyToStart;
		if (time == 0) {
			isRecipeReadyToStart = canStartProcess(recipe, recipeWrapper, outputHandler) && recipeData.getConsumptionOnStart(recipe) <= energyStorage.getEnergy();
		} else {
			isRecipeReadyToStart = true; // The recipe has already started, so the conditions must be true
		}
		return isRecipeReadyToStart && recipeData.getConsumptionPerTick(recipe) <= energyStorage.getEnergy();
	}
	
	protected boolean canStartProcess(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler) {
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
		buffer.writeFloat(MathUtil.valueInRange(0, 1, time / (float) totalTime));
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
