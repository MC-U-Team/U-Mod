package info.u_team.u_mod.types;

import java.util.function.Supplier;

import info.u_team.u_mod.api.machines.ISolarPanel;
import net.minecraft.item.EnumRarity;

public enum EnumSolarPanel implements ISolarPanel {
	
	;
	
	private final EnumRarity rarity;
	private final String name;
	private final Supplier<Integer> capacity;
	private final Supplier<Integer> transfer;
	private final Supplier<Integer> generation;
	
	private EnumSolarPanel(EnumRarity rarity, String name, Supplier<Integer> capacity, Supplier<Integer> transfer, Supplier<Integer> generation) {
		this.rarity = rarity;
		this.name = name;
		this.capacity = capacity;
		this.transfer = transfer;
		this.generation = generation;
	}
	
	@Override
	public EnumRarity getRarity() {
		return rarity;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getCapacity() {
		return capacity.get();
	}
	
	@Override
	public int getTransfer() {
		return transfer.get();
	}
	
	@Override
	public int getGeneration() {
		return generation.get();
	}
	
}
