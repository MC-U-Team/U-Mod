package info.u_team.u_mod.recipe.machine;

import java.util.*;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.tilentity.machine.TileEntityMachine;
import net.minecraft.item.ItemStack;

public class RecipePulverizer implements IMachineRecipe {
	
	private List<List<ItemStack>> jeiitemingredients;
	private List<ItemStack> jeiitemoutputs;
	
	private IngredientItemStack ingredient;
	private OutputItemStack firstoutput, secondoutput, thirdoutput;
	private ProcessEnergy energy;
	private ProcessTime time;
	
	public RecipePulverizer(IngredientItemStack ingredient, OutputItemStack firstoutput, ProcessEnergy energy, ProcessTime time) {
		this(ingredient, firstoutput, new OutputItemStack(ItemStack.EMPTY), new OutputItemStack(ItemStack.EMPTY), energy, time);
	}
	
	public RecipePulverizer(IngredientItemStack ingredient, OutputItemStack firstoutput, OutputItemStack secondoutput, ProcessEnergy energy, ProcessTime time) {
		this(ingredient, firstoutput, secondoutput, new OutputItemStack(ItemStack.EMPTY), energy, time);
	}
	
	public RecipePulverizer(IngredientItemStack ingredient, OutputItemStack firstoutput, OutputItemStack secondoutput, OutputItemStack thirdoutput, ProcessEnergy energy, ProcessTime time) {
		this.ingredient = ingredient;
		this.firstoutput = firstoutput;
		this.secondoutput = secondoutput;
		this.thirdoutput = thirdoutput;
		this.energy = energy;
		this.time = time;
		
		if (ingredient.getItemStack().get(0).equals(ItemStack.EMPTY)) {
			throw new IllegalArgumentException("The one and only ingredient can't be empty");
		}
		
		jeiitemingredients = new ArrayList<>();
		jeiitemingredients.add(ingredient.getItemStack());
		
		jeiitemoutputs = new ArrayList<>();
		jeiitemoutputs.add(firstoutput.getItemStack());
		jeiitemoutputs.add(secondoutput.getItemStack());
		jeiitemoutputs.add(thirdoutput.getItemStack());
	}
	
	@Override
	public boolean areIngredientsMatching(TileEntityMachine machine) {
		return ingredient.containsStackCountMatchOrHigher(machine.getStackInSlot(0));
	}
	
	@Override
	public boolean areOutputsMatching(TileEntityMachine machine) {
		return firstoutput.isItemStackAcceptable(machine.getStackInSlot(1)) && secondoutput.isItemStackAcceptable(machine.getStackInSlot(2)) && thirdoutput.isItemStackAcceptable(machine.getStackInSlot(3));
	}
	
	@Override
	public boolean isEnergyMatching(TileEntityMachine machine) {
		return machine.getStorage().getEnergyStored() >= energy.getEnergy();
	}
	
	@Override
	public void execute(TileEntityMachine machine) {
		ingredient.execute(machine, 0);
		firstoutput.execute(machine, 1);
		secondoutput.execute(machine, 2);
		thirdoutput.execute(machine, 3);
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
