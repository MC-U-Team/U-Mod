package info.u_team.u_mod.util.recipe;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

import info.u_team.u_mod.item.basic.BasicMachineUpgradeItem;
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
	
	private final BasicEnergyStorage energy;
	private final UItemStackHandler ingredientSlots;
	private final UItemStackHandler outputSlots;
	private final UItemStackHandler upgradeSlots;
	
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
	
	public RecipeHandler(IRecipeType<T> recipeType, BasicEnergyStorage energy, UItemStackHandler ingredientSlots, UItemStackHandler outputSlots, UItemStackHandler upgradeSlots, RecipeData<T> recipeData, Runnable dirtyMarker) {
		this.energy = energy;
		this.ingredientSlots = ingredientSlots;
		this.outputSlots = outputSlots;
		this.upgradeSlots = upgradeSlots;
		this.recipeData = recipeData;
		this.dirtyMarker = dirtyMarker;
		
		energyOptional = LazyOptional.of(() -> energy);
		ingredientSlotsOptional = LazyOptional.of(() -> ingredientSlots);
		outputSlotsOptional = LazyOptional.of(() -> outputSlots);
		upgradeSlotsOptional = LazyOptional.of(() -> upgradeSlots);
		
		recipeCache = new RecipeCache<>(recipeType, ingredientSlots.getSlots());
	}
	
	public void update(World world) {
		final RecipeWrapper recipeWrapper = new RecipeWrapper(ingredientSlots);
		
		// Recipe optional
		final Optional<T> recipeOptional = recipeCache.getRecipe(world, recipeWrapper);
		
		// If no recipe was found we cannot proceed
		if (!recipeOptional.isPresent()) {
			resetTimeAndMarkDirty();
			return;
		}
		
		// Get recipe
		final T recipe = recipeOptional.get();
		
		// Get all upgrade functions
		final List<Function<Integer, Integer>> timeFunctions = new ArrayList<>();
		final List<Function<Integer, Integer>> startConsumptionFunctions = new ArrayList<>();
		final List<Function<Integer, Integer>> tickConsumptionFunctions = new ArrayList<>();
		
		for (int index = 0; index < upgradeSlots.getSlots(); index++) {
			final ItemStack stack = upgradeSlots.getStackInSlot(index);
			if (stack.getItem() instanceof BasicMachineUpgradeItem) {
				final BasicMachineUpgradeItem item = (BasicMachineUpgradeItem) stack.getItem();
				timeFunctions.add(item.applyTimeUpgrade(stack.getCount()));
				startConsumptionFunctions.add(item.applyStartConsumptionUpgrade(stack.getCount()));
				tickConsumptionFunctions.add(item.applyTickConsumptionUpgrade(stack.getCount()));
			}
		}
		
		// Set the total time to the total time from the recipe (trough the function for modifiers)
		totalTime = totalTimeModifier.apply(recipe, recipeData.getTotalTime(recipe));
		
		for (int index = 0; index < timeFunctions.size(); index++) {
			totalTime = timeFunctions.get(index).apply(totalTime);
		}
		
		// Check if the recipe is valid when the timer starts
		if (time == 0) {
			if (!isRecipeValid(recipe, recipeWrapper)) {
				time = 0;
				return;
			}
		}
		
		// Check if we can process (e.g. output slot is not full)
		if (!canProcess(recipe, recipeWrapper, outputSlots)) {
			time = 0;
			return;
		}
		
		// If we have no energy for the consumption at the start we cannot proceed
		if (time == 0) {
			if (!doConsumtionOnStart(recipe, energy)) {
				time = 0;
				return;
			}
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
		dirtyMarker.run();
	}
	
	private void resetTimeAndMarkDirty() {
		time = 0;
		dirtyMarker.run();
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
		for (int recipeIndex = 0; recipeIndex < recipeOutputs.size(); recipeIndex++) {
			final ItemStack recipeOutput = recipeOutputs.get(recipeIndex);
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
			// If recipe output is empty we continue the loop
			if (recipeOutput.isEmpty()) {
				continue;
			}
			System.out.println(ItemHandlerHelper.insertItemStacked(outputHandler, recipeOutput.copy(), false));
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
		compound.put("ingredient", ingredientSlots.serializeNBT());
		compound.put("output", outputSlots.serializeNBT());
		compound.put("upgrades", upgradeSlots.serializeNBT());
		compound.putInt("time", time);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		ingredientSlots.deserializeNBT(compound.getCompound("ingredient"));
		outputSlots.deserializeNBT(compound.getCompound("output"));
		upgradeSlots.deserializeNBT(compound.getCompound("upgrades"));
		time = compound.getInt("time");
	}
	
	public void invalidate() {
		energyOptional.invalidate();
		ingredientSlotsOptional.invalidate();
		ingredientSlotsOptional.invalidate();
	}
	
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeFloat(time / (float) totalTime);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		percent = buffer.readFloat();
	}
	
	// Getter
	public BasicEnergyStorage getEnergy() {
		return energy;
	}
	
	public UItemStackHandler getIngredientSlots() {
		return ingredientSlots;
	}
	
	public UItemStackHandler getOutputSlots() {
		return outputSlots;
	}
	
	public UItemStackHandler getUpgradeSlots() {
		return upgradeSlots;
	}
	
	public LazyOptional<BasicEnergyStorage> getEnergyOptional() {
		return energyOptional;
	}
	
	public LazyOptional<UItemStackHandler> getIngredientSlotsOptional() {
		return ingredientSlotsOptional;
	}
	
	public LazyOptional<UItemStackHandler> getOutputSlotsOptional() {
		return outputSlotsOptional;
	}
	
	public LazyOptional<UItemStackHandler> getUpgradeSlotsOptional() {
		return upgradeSlotsOptional;
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
