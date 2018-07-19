package info.u_team.u_mod.recipe.machine;

import java.util.*;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.tilentity.machine.TileEntityMachine;
import net.minecraft.item.ItemStack;

public class RecipeEnricher implements IMachineRecipe {
	
	private List<List<ItemStack>> jeiitemingredients;
	private List<ItemStack> jeiitemoutputs;
	
	private IngredientItemStack ingredient;
	private OutputItemStack output;
	private ProcessEnergy energy;
	private ProcessTime time;
	
	public RecipeEnricher(IngredientItemStack ingredient, OutputItemStack output, ProcessEnergy energy, ProcessTime time) {
		this.ingredient = ingredient;
		this.output = output;
		this.energy = energy;
		this.time = time;
		
		if (ingredient.getItemStack().get(0).equals(ItemStack.EMPTY)) {
			throw new IllegalArgumentException("The one and only ingredient can't be empty");
		}
		
		jeiitemingredients = new ArrayList<>();
		jeiitemingredients.add(ingredient.getItemStack());
		
		jeiitemoutputs = new ArrayList<>();
		jeiitemoutputs.add(output.getItemStack());
	}
	
	@Override
	public boolean areIngredientsMatching(TileEntityMachine machine) {
		return ingredient.containsStackCountMatchOrHigher(machine.getStackInSlot(0));
	}
	
	@Override
	public boolean areOutputsMatching(TileEntityMachine machine) {
		return output.isItemStackAcceptable(machine.getStackInSlot(1));
	}
	
	@Override
	public boolean isEnergyMatching(TileEntityMachine machine) {
		return machine.getStorage().getEnergyStored() >= energy.getEnergy();
	}
	
	@Override
	public void execute(TileEntityMachine machine) {
		ingredient.execute(machine, 0);
		output.execute(machine, 1);
		machine.getStorage().extractEnergy(energy.getEnergy(), false);
	}
	
	@Override
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
