package info.u_team.u_mod.api;

import java.util.function.Predicate;
import java.util.stream.Stream;

import info.u_team.u_team_core.util.Predicates;
import net.minecraft.fluid.*;
import net.minecraft.tags.Tag;
import net.minecraftforge.fluids.FluidStack;

public class FluidIngredient implements Predicate<FluidStack> {
	
	private final FluidStack[] matchingFluids;
	private final int amount;
	
	public static FluidIngredient fromFluids(int amount, Fluid... fluids) {
		return new FluidIngredient(Stream.of(fluids).map(fluid -> new FluidStack(fluid, 1000)), amount);
	}
	
	public static FluidIngredient fromStacks(int amount, FluidStack... fluids) {
		return new FluidIngredient(Stream.of(fluids), amount);
	}
	
	public static FluidIngredient fromTag(int amount, Tag<Fluid> tag) {
		if (tag.getAllElements().size() == 0) { // Prevent no fluid usage when tag is empty
			return new FluidIngredient(Stream.of(new FluidStack(Fluids.LAVA, 1)), Integer.MAX_VALUE);
		}
		return new FluidIngredient(tag.getAllElements().stream().map(fluid -> new FluidStack(fluid, 1000)), amount);
	}
	
	protected FluidIngredient(Stream<FluidStack> stream, int amount) {
		this.matchingFluids = stream.filter(Predicates.not(FluidStack::isEmpty)).toArray(FluidStack[]::new);
		this.amount = amount;
	}
	
	@Override
	public boolean test(FluidStack stack) {
		if (stack == null) {
			return false;
		} else if (matchingFluids.length == 0) {
			return stack.isEmpty();
		} else {
			for (FluidStack fluidStack : matchingFluids) {
				if (fluidStack.getFluid() == stack.getFluid()) {
					return stack.getAmount() >= amount;
				}
			}
			return false;
		}
	}
}
