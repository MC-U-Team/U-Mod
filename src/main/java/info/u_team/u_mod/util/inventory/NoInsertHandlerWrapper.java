package info.u_team.u_mod.util.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class NoInsertHandlerWrapper implements IItemHandlerModifiable {
	
	private final IItemHandlerModifiable handler;
	
	public NoInsertHandlerWrapper(IItemHandlerModifiable handler) {
		this.handler = handler;
	}
	
	@Override
	public int getSlots() {
		return handler.getSlots();
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return handler.getStackInSlot(index);
	}
	
	@Override
	public ItemStack insertItem(int index, ItemStack stack, boolean simulate) {
		return ItemStack.EMPTY; // Disable insertion
	}
	
	@Override
	public ItemStack extractItem(int index, int amount, boolean simulate) {
		return handler.extractItem(index, amount, simulate);
	}
	
	@Override
	public int getSlotLimit(int index) {
		return handler.getSlotLimit(index);
	}
	
	@Override
	public boolean isItemValid(int index, ItemStack stack) {
		return handler.isItemValid(index, stack);
	}
	
	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		// Disable set stack
	}
	
}
