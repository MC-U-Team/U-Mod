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
		if (!ingredientSlots.isPresent() || !outputSlots.isPresent() || !energy.isPresent()) {
			time = 0;
			return;
		}
		final RecipeWrapper recipeWrapper = new RecipeWrapper(ingredientSlots.orElseThrow(IllegalStateException::new));
		final UItemStackHandler outputHandler = outputSlots.orElseThrow(IllegalStateException::new);
		final BasicEnergyStorage energyStorage = energy.orElseThrow(IllegalStateException::new);
		
		final Optional<T> recipeOptional = recipeCache.getRecipe(world, recipeWrapper);
		if (recipeOptional.isPresent()) {
			final T recipe = recipeOptional.get();
			totalTime = recipeData.getTotalTime(recipe);
			if (canProcess(recipe, recipeWrapper, outputHandler)) {
				if (time == 0) {
					energyStorage.addEnergy(-recipeData.getConsumptionOnStart(recipe));
				}
				time++;
				if (time == totalTime) {
					time = 0;
					process(recipe, recipeWrapper, outputHandler);
				}
			} else {
				time = 0;
			}
		} else {
			time = 0;
		}
	}
	
	protected boolean canProcess(T recipe, RecipeWrapper recipeWrapper, UItemStackHandler outputHandler) {
		final NonNullList<ItemStack> recipeOutputs = recipeData.getRecipeOutputs(recipe, recipeWrapper);
		if (recipeOutputs.isEmpty() || recipeOutputs.stream().allMatch(ItemStack::isEmpty)) {
			return false;
		}
		for (int index = 0; index < recipeOutputs.size(); index++) {
			final ItemStack recipeOutput = recipeOutputs.get(index);
			final ItemStack slotOutput = outputHandler.getStackInSlot(index);
			
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
			if (recipeOutput.isEmpty()) {
				continue;
			}
			final ItemStack outputStack = outputHandler.getStackInSlot(index);
			if (outputStack.isEmpty()) {
				outputHandler.setStackInSlot(0, recipeOutput.copy());
			} else if (outputStack.getItem() == recipeOutput.getItem()) {
				outputStack.grow(recipeOutput.getCount());
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
