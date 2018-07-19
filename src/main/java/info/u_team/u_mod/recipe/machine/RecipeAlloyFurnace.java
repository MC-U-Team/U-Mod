package info.u_team.u_mod.recipe.machine;

import java.util.*;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.tilentity.machine.TileEntityMachine;
import net.minecraft.item.ItemStack;

public class RecipeAlloyFurnace implements IMachineRecipe {
	
	private List<List<ItemStack>> jeiitemingredients;
	private List<ItemStack> jeiitemoutputs;
	
	private IngredientItemStack firstingredient, secondingredient, thirdingredient;
	private OutputItemStack output;
	private ProcessEnergy energy;
	private ProcessTime time;
	
	public RecipeAlloyFurnace(IngredientItemStack firstingredient, IngredientItemStack secondingredient, IngredientItemStack thirdingredient, OutputItemStack output, ProcessEnergy energy, ProcessTime time) {
		this.firstingredient = firstingredient;
		this.secondingredient = secondingredient;
		this.thirdingredient = thirdingredient;
		this.output = output;
		this.energy = energy;
		this.time = time;
		
		if (firstingredient.getItemStack().get(0).equals(ItemStack.EMPTY) && secondingredient.getItemStack().get(0).equals(ItemStack.EMPTY) && thirdingredient.getItemStack().get(0).equals(ItemStack.EMPTY)) {
			throw new IllegalArgumentException("Not every ingredient can be empty");
		}
		
		jeiitemingredients = new ArrayList<>();
		jeiitemingredients.add(firstingredient.getItemStack());
		jeiitemingredients.add(secondingredient.getItemStack());
		jeiitemingredients.add(thirdingredient.getItemStack());
		
		jeiitemoutputs = new ArrayList<>();
		jeiitemoutputs.add(output.getItemStack());
	}
	
	public boolean areIngredientsMatching(TileEntityMachine machine) {
		return firstingredient.containsStackCountMatchOrHigher(machine.getStackInSlot(0)) && secondingredient.containsStackCountMatchOrHigher(machine.getStackInSlot(1)) && thirdingredient.containsStackCountMatchOrHigher(machine.getStackInSlot(2));
	}
	
	public boolean areOutputsMatching(TileEntityMachine machine) {
		return output.isItemStackAcceptable(machine.getStackInSlot(3));
	}
	
	public boolean isEnergyMatching(TileEntityMachine machine) {
		return machine.getStorage().getEnergyStored() >= energy.getEnergy();
	}
	
	public void execute(TileEntityMachine machine) {
		firstingredient.execute(machine, 0);
		secondingredient.execute(machine, 1);
		thirdingredient.execute(machine, 2);
		output.execute(machine, 3);
		machine.getStorage().extractEnergy(energy.getEnergy(), false);
	}
	
	public int getTime() {
		return time.getTime();
	}
	
	public List<List<ItemStack>> getJeiItemIngredients() {
		return jeiitemingredients;
	}
	
	public List<ItemStack> getJeiItemOutputs() {
		return jeiitemoutputs;
	}
	
}
