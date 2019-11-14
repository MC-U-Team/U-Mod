package info.u_team.u_mod.util.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class NoExtractHandlerWrapper implements IItemHandlerModifiable {
	
	private final IItemHandlerModifiable handler;
	
	public NoExtractHandlerWrapper(IItemHandlerModifiable handler) {
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
		return handler.insertItem(index, stack, simulate);
	}
	
	@Override
	public ItemStack extractItem(int index, int amount, boolean simulate) {
		return ItemStack.EMPTY; // Disable extraction
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
		handler.setStackInSlot(slot, stack);
	}
	
}
