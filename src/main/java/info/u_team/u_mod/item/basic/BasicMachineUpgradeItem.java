package info.u_team.u_mod.item.basic;

import java.util.function.Function;

import info.u_team.u_mod.init.UModItemGroups;
import info.u_team.u_team_core.item.UItem;

public abstract class BasicMachineUpgradeItem extends UItem {
	
	protected final int maxStackSize;
	
	public BasicMachineUpgradeItem(String name, int maxStackSize) {
		super(name, UModItemGroups.GROUP, new Properties().maxStackSize(maxStackSize));
		this.maxStackSize = maxStackSize;
	}
	
	public abstract Function<Integer, Integer> applyTimeUpgrade(int count);
	
	public abstract Function<Integer, Integer> applyStartConsumptionUpgrade(int count);
	
	public abstract Function<Integer, Integer> applyTickConsumptionUpgrade(int count);
	
}
