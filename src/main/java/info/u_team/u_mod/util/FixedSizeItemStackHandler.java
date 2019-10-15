package info.u_team.u_mod.util;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.*;

public class FixedSizeItemStackHandler implements IItemHandlerModifiable, INBTSerializable<CompoundNBT> {
	
	private final NonNullList<ItemStack> stacks;
	
	public FixedSizeItemStackHandler(int size) {
		stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		if (!isItemValid(slot, stack)) {
			return stack;
		}
		
		validateIndex(slot);
		
		final ItemStack existing = stacks.get(slot);
		int limit = getStackLimit(slot, stack);
		
		if (!existing.isEmpty()) {
			if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) {
				return stack;
			}
			
			limit -= existing.getCount();
		}
		
		if (limit <= 0) {
			return stack;
		}
		
		final boolean reachedLimit = stack.getCount() > limit;
		
		if (!simulate) {
			if (existing.isEmpty()) {
				stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
			} else {
				existing.grow(reachedLimit ? limit : stack.getCount());
			}
			slotChanged(slot);
		}
		
		return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount == 0) {
			return ItemStack.EMPTY;
		}
		
		validateIndex(slot);
		
		final ItemStack existing = stacks.get(slot);
		
		if (existing.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		final int toExtract = Math.min(amount, existing.getMaxStackSize());
		
		if (existing.getCount() <= toExtract) {
			if (!simulate) {
				stacks.set(slot, ItemStack.EMPTY);
				slotChanged(slot);
			}
			return existing;
		} else {
			if (!simulate) {
				stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
				slotChanged(slot);
			}
			return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
		}
	}
	
	@Override
	public int getSlots() {
		return stacks.size();
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return stacks.get(index);
	}
	
	@Override
	public void setStackInSlot(int index, ItemStack stack) {
		validateIndex(index);
		stacks.set(index, stack);
		slotChanged(index);
	}
	
	@Override
	public boolean isItemValid(int index, ItemStack stack) {
		return true;
	}
	
	@Override
	public int getSlotLimit(int index) {
		return 64;
	}
	
	protected int getStackLimit(int index, ItemStack stack) {
		return Math.min(getSlotLimit(index), stack.getMaxStackSize());
	}
	
	protected void validateIndex(int index) {
		if (index < 0 || index >= stacks.size()) {
			throw new RuntimeException("Slot " + index + " not in valid range");
		}
	}
	
	protected void slotChanged(int index) {
	}
	
	// If used in tileentites, then the world might be null
	protected void onLoaded() {
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		return ItemStackHelper.saveAllItems(new CompoundNBT(), stacks, false);
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		ItemStackHelper.loadAllItems(compound, stacks);
		onLoaded();
	}
	
}
