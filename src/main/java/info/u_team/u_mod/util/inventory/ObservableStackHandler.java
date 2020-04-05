package info.u_team.u_mod.util.inventory;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import info.u_team.u_team_core.inventory.UItemStackHandler;

public class ObservableStackHandler extends UItemStackHandler {
	
	private final List<Consumer<Integer>> contentChangedObserverList;
	
	public ObservableStackHandler(int size) {
		super(size);
		contentChangedObserverList = new ArrayList<>();
	}
	
	@Override
	protected void onContentsChanged(int slot) {
		contentChangedObserverList.forEach(entry -> entry.accept(slot));
	}
	
	public boolean isEmpty() {
		return IntStream.range(0, getSlots()).allMatch(index -> getStackInSlot(index).isEmpty());
	}
	
	public void addObserver(Consumer<Integer> consumer) {
		contentChangedObserverList.add(consumer);
	}
	
	public void removeObserver(Consumer<Integer> consumer) {
		contentChangedObserverList.remove(consumer);
	}
	
}
