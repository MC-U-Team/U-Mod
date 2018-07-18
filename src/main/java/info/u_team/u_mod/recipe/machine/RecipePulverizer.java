package info.u_team.u_mod.recipe.machine;

import java.util.*;

import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.tilentity.TileEntityPulverizer;
import net.minecraft.item.ItemStack;

public class RecipePulverizer {
	
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
	
	public boolean areIngredientsMatching(TileEntityPulverizer pulverizer) {
		return ingredient.containsStackCountMatchOrHigher(pulverizer.getStackInSlot(0));
	}
	
	public boolean areOutputsMatching(TileEntityPulverizer pulverizer) {
		return firstoutput.isItemStackAcceptable(pulverizer.getStackInSlot(1)) && secondoutput.isItemStackAcceptable(pulverizer.getStackInSlot(2)) && thirdoutput.isItemStackAcceptable(pulverizer.getStackInSlot(3));
	}
	
	public boolean isEnergyMatching(TileEntityPulverizer pulverizer) {
		return pulverizer.getStorage().getEnergyStored() >= energy.getEnergy();
	}
	
	public void execute(TileEntityPulverizer pulverizer) {
		ingredient.execute(pulverizer, 0);
		firstoutput.execute(pulverizer, 1);
		secondoutput.execute(pulverizer, 2);
		thirdoutput.execute(pulverizer, 3);
		pulverizer.getStorage().extractEnergy(energy.getEnergy(), false);
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
