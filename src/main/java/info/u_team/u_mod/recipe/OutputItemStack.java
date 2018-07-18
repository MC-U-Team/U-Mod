package info.u_team.u_mod.recipe;

import info.u_team.u_mod.util.ItemUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class OutputItemStack {
	
	private ItemStack stack;
	
	public OutputItemStack(ItemStack stack) {
		if (stack == null) {
			throw new IllegalStateException("Stack can't be null. Use ItemStack.EMPTY");
		}
		this.stack = stack;
	}
	
	public ItemStack getItemStack() {
		return stack;
	}
	
	public boolean isItemStackAcceptable(ItemStack other) {
		return ((other.isEmpty() || ItemUtil.areItemStacksEqualIgnoreAmount(stack, other) && other.isStackable() && other.getCount() + stack.getCount() <= other.getMaxStackSize()));
	}
	
	public void execute(IInventory inventory, int slot) {
		if (stack.isEmpty()) {
			return;
		}
		ItemStack other = inventory.getStackInSlot(slot);
		if (other.isEmpty()) {
			inventory.setInventorySlotContents(slot, stack.copy());
		} else {
			other.grow(stack.getCount());
		}
	}
	
}
