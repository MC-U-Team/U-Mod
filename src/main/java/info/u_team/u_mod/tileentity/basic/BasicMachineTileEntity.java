package info.u_team.u_mod.tileentity.basic;

import info.u_team.u_mod.util.inventory.InputOutputHandlerWrapper;
import info.u_team.u_mod.util.recipe.*;
import info.u_team.u_team_core.inventory.*;
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
	
	protected final UItemStackHandler ingredientSlots;
	protected final UItemStackHandler outputSlots;
	protected final UItemStackHandler upgradeSlots;
	
	protected final LazyOptional<UItemStackHandler> ingredientSlotsOptional;
	protected final LazyOptional<UItemStackHandler> outputSlotsOptional;
	protected final LazyOptional<UItemStackHandler> upgradeSlotsOptional;
	
	protected final RecipeHandler<T> recipeHandler;
	
	protected final LazyOptional<InputOutputHandlerWrapper> slotsWrapperOptional;
	
	public BasicMachineTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, IRecipeType<T> recipeType, int ingredientSize, int outputSize, int upgradeSize, RecipeData<T> recipeData) {
		super(type, capacity, maxReceive, maxExtract);
		
		ingredientSlots = new TileEntityUItemStackHandler(ingredientSize, this);
		outputSlots = new TileEntityUItemStackHandler(outputSize, this);
		upgradeSlots = new TileEntityUItemStackHandler(upgradeSize, this);
		
		ingredientSlotsOptional = LazyOptional.of(() -> ingredientSlots);
		outputSlotsOptional = LazyOptional.of(() -> outputSlots);
		upgradeSlotsOptional = LazyOptional.of(() -> upgradeSlots);
		
		recipeHandler = new TileEntityRecipeHandler<T, BasicEnergyTileEntity>(this, recipeType, ingredientSize, ingredientSlotsOptional, outputSlotsOptional, upgradeSlotsOptional, recipeData);
		slotsWrapperOptional = LazyOptional.of(() -> new InputOutputHandlerWrapper(ingredientSlots, outputSlots));
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
		compound.put("ingredients", ingredientSlots.serializeNBT());
		compound.put("outputs", outputSlots.serializeNBT());
		compound.put("upgrades", upgradeSlots.serializeNBT());
		compound.put("recipe", recipeHandler.serializeNBT());
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
		ingredientSlots.deserializeNBT(compound.getCompound("ingredients"));
		outputSlots.deserializeNBT(compound.getCompound("outputs"));
		upgradeSlots.deserializeNBT(compound.getCompound("upgrades"));
		recipeHandler.deserializeNBT(compound.getCompound("recipe"));
	}
	
	// Invalidate lazy optional
	
	@Override
	public void remove() {
		super.remove();
		ingredientSlotsOptional.invalidate();
		outputSlotsOptional.invalidate();
		upgradeSlotsOptional.invalidate();
		slotsWrapperOptional.invalidate();
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
	
	public UItemStackHandler getIngredientSlots() {
		return ingredientSlots;
	}
	
	public UItemStackHandler getOutputSlots() {
		return outputSlots;
	}
	
	public UItemStackHandler getUpgradeSlots() {
		return upgradeSlots;
	}
	
	public RecipeHandler<T> getRecipeHandler() {
		return recipeHandler;
	}
	
	// Capability
	@Override
	public <X> LazyOptional<X> getCapability(Capability<X> capability, Direction side) {
		if (capability == CapabilityEnergy.ENERGY) {
			return internalEnergyStorageOptional.cast();
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slotsWrapperOptional.cast();
		} else {
			return super.getCapability(capability, side);
		}
	}
	
}
