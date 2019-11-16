package info.u_team.u_mod.util.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class InputOutputHandlerWrapper implements IItemHandlerModifiable {
	
	private final IItemHandlerModifiable input;
	private final IItemHandlerModifiable output;
	
	public InputOutputHandlerWrapper(IItemHandlerModifiable input, IItemHandlerModifiable output) {
		this.input = input;
		this.output = output;
	}
	
	@Override
	public int getSlots() {
		return input.getSlots() + output.getSlots();
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < input.getSlots()) {
			return input.getStackInSlot(index);
		}
		return output.getStackInSlot(index - input.getSlots());
	}
	
	@Override
	public ItemStack insertItem(int index, ItemStack stack, boolean simulate) {
		if (index < input.getSlots()) {
			return input.insertItem(index, stack, simulate);
		}
		return stack;
	}
	
	@Override
	public ItemStack extractItem(int index, int amount, boolean simulate) {
		if (index < input.getSlots()) {
			return ItemStack.EMPTY;
		}
		return output.extractItem(index - input.getSlots(), amount, simulate);
	}
	
	@Override
	public int getSlotLimit(int index) {
		if (index < input.getSlots()) {
			return input.getSlotLimit(index);
		}
		return output.getSlotLimit(index - input.getSlots());
	}
	
	@Override
	public boolean isItemValid(int index, ItemStack stack) {
		if (index < input.getSlots()) {
			return input.isItemValid(index, stack);
		}
		return false;
	}
	
	@Override
	public void setStackInSlot(int index, ItemStack stack) {
		if (index < input.getSlots()) {
			input.setStackInSlot(index, stack);
		}
	}
}
