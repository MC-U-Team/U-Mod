package info.u_team.u_mod.item;

import java.util.function.Function;

import info.u_team.u_mod.item.basic.BasicMachineUpgradeItem;
import net.minecraft.item.Rarity;

public class TimeMachineUpgradeItem extends BasicMachineUpgradeItem {
	
	public TimeMachineUpgradeItem(String name, int maxStackSize, Rarity rarity) {
		super(name, maxStackSize, rarity);
	}
	
	@Override
	public Function<Integer, Integer> applyTimeUpgrade(int count) {
		final int value = validateStackSize(count);
		final float multiplier = (-(float) Math.log(value) + 5) / 5;
		return time -> (int) (time * multiplier);
	}
	
	@Override
	public Function<Integer, Integer> applyStartConsumptionUpgrade(int count) {
		final int value = validateStackSize(count);
		return consumption -> consumption + consumption * value;
	}
	
	@Override
	public Function<Integer, Integer> applyTickConsumptionUpgrade(int count) {
		final int value = validateStackSize(count);
		return consumption -> consumption + (int) (consumption * (value / 2F));
	}
	
}
