package info.u_team.u_mod.api.recipe;

import info.u_team.u_mod.api.inventory.IGeneralInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class GeneralRecipeWrapper implements IGeneralInventory {
	
	protected final IItemHandlerModifiable itemInventory;
	protected final IFluidHandler fluidInventory;
	
	public GeneralRecipeWrapper(IItemHandlerModifiable itemInventory, IFluidHandler fluidInventory) {
		this.itemInventory = itemInventory;
		this.fluidInventory = fluidInventory;
	}
	
	// Item inventory
	
	@Override
	public int getSizeInventory() {
		return itemInventory.getSlots();
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return itemInventory.getStackInSlot(slot);
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count) {
		final ItemStack stack = itemInventory.getStackInSlot(slot);
		return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		itemInventory.setStackInSlot(slot, stack);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		final ItemStack stack = getStackInSlot(index);
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}
	
	@Override
	public boolean isItemsEmpty() {
		for (int i = 0; i < itemInventory.getSlots(); i++) {
			if (!itemInventory.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return itemInventory.isItemValid(slot, stack);
	}
	
	@Override
	public void clear() {
		for (int index = 0; index < itemInventory.getSlots(); index++) {
			itemInventory.setStackInSlot(index, ItemStack.EMPTY);
		}
	}
	
	// Fluid inventory
	
	@Override
	public int getTanks() {
		return fluidInventory.getTanks();
	}
	
	@Override
	public FluidStack getFluidInTank(int tank) {
		return fluidInventory.getFluidInTank(tank);
	}
	
	@Override
	public int getTankCapacity(int tank) {
		return fluidInventory.getTankCapacity(tank);
	}
	
	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return fluidInventory.isFluidValid(tank, stack);
	}
	
	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return fluidInventory.fill(resource, action);
	}
	
	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		return fluidInventory.drain(resource, action);
	}
	
	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return fluidInventory.drain(maxDrain, action);
	}
	
	@Override
	public boolean isFluidsEmpty() {
		return false;
	}
	
	// Has inventory
	
	@Override
	public boolean hasItems() {
		return itemInventory != null;
	}
	
	@Override
	public boolean hasFluids() {
		return fluidInventory != null;
	}
	
	// Unused methods
	
	@Override
	public void markDirty() {
	}
	
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return false;
	}
}
