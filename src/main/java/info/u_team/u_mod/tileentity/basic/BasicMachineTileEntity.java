package info.u_team.u_mod.tileentity.basic;

import info.u_team.u_mod.util.inventory.*;
import info.u_team.u_mod.util.recipe.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class BasicMachineTileEntity<T extends IRecipe<IInventory>> extends BasicContainerEnergyTileEntity {
	
	protected final RecipeHandler<T> recipeHandler;
	
	protected final LazyOptional<NoExtractHandlerWrapper> ingredientSlotsWrapper;
	protected final LazyOptional<NoInsertHandlerWrapper> outputSlotsWrapper;
	
	public BasicMachineTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, IRecipeType<T> recipeType, int ingredientSize, int outputSize, RecipeData<T> recipeData) {
		super(type, capacity, maxReceive, maxExtract);
		recipeHandler = new TileEntityRecipeHandler<T, BasicEnergyTileEntity>(this, recipeType, ingredientSize, outputSize, recipeData);
		ingredientSlotsWrapper = recipeHandler.getIngredient().map(NoExtractHandlerWrapper::new);
		outputSlotsWrapper = recipeHandler.getIngredient().map(NoInsertHandlerWrapper::new);
	}
	
	// Tick
	@Override
	protected void tickServer() {
		recipeHandler.update(world);
	}
	
	// NBT
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		super.writeNBT(compound);
		compound.put("recipe_data", recipeHandler.serializeNBT());
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
		recipeHandler.deserializeNBT(compound.getCompound("recipe_data"));
	}
	
	// Invalidate lazy optional
	
	@Override
	public void remove() {
		super.remove();
		recipeHandler.invalidate();
		ingredientSlotsWrapper.invalidate();
		outputSlotsWrapper.invalidate();
	}
	
	// Inital send when container is opened
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		super.sendInitialDataBuffer(buffer);
		recipeHandler.sendInitialDataBuffer(buffer);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		super.handleInitialDataBuffer(buffer);
		recipeHandler.handleInitialDataBuffer(buffer);
	}
	
	// Getter
	public RecipeHandler<T> getRecipeHandler() {
		return recipeHandler;
	}
	
	// Capability
	@Override
	public <X> LazyOptional<X> getCapability(Capability<X> capability, Direction side) {
		if (capability == CapabilityEnergy.ENERGY) {
			return internalEnergyStorage.cast();
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (side == Direction.UP) {
				return ingredientSlotsWrapper.cast();
			} else {
				return outputSlotsWrapper.cast();
			}
		} else {
			return super.getCapability(capability, side);
		}
	}
	
}
