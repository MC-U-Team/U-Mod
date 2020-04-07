package info.u_team.u_mod.util.inventory;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import info.u_team.u_team_core.inventory.UItemStackHandler;
import net.minecraft.item.ItemStack;

public class ObservableStackHandler extends UItemStackHandler {
	
	private final List<Consumer<Integer>> itemChangedObserverList;
	
	public ObservableStackHandler(int size) {
		super(size);
		itemChangedObserverList = new ArrayList<>();
	}
	
	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		final ItemStack previousStack = getStackInSlot(slot);
		super.setStackInSlot(slot, stack);
		if (hasStackChanged(previousStack, stack)) {
			itemChanged(slot);
		}
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		final ItemStack previousStack = getStackInSlot(slot);
		final ItemStack toReturn = insertItem(slot, stack, simulate);
		if (hasStackChanged(previousStack, getStackInSlot(slot))) {
			itemChanged(slot);
		}
		return toReturn;
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		final ItemStack previousStack = getStackInSlot(slot);
		final ItemStack toReturn = extractItem(slot, amount, simulate);
		if (hasStackChanged(previousStack, getStackInSlot(slot))) {
			itemChanged(slot);
		}
		return toReturn;
	}
	
	public boolean isEmpty() {
		return IntStream.range(0, getSlots()).allMatch(index -> getStackInSlot(index).isEmpty());
	}
	
	private boolean hasStackChanged(ItemStack a, ItemStack b) {
		return !ItemStack.areItemStacksEqual(a, b);
	}
	
	protected void itemChanged(int slot) {
		itemChangedObserverList.forEach(entry -> entry.accept(slot));
	}
	
	public void addObserver(Consumer<Integer> consumer) {
		itemChangedObserverList.add(consumer);
	}
	
	public void removeObserver(Consumer<Integer> consumer) {
		itemChangedObserverList.remove(consumer);
	}
	
}
