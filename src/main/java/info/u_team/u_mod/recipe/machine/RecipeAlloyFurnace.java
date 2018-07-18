package info.u_team.u_mod.recipe.machine;

import java.util.*;

import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.tilentity.machine.TileEntityAlloyFurnace;
import net.minecraft.item.ItemStack;

public class RecipeAlloyFurnace {
	
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
	
	public boolean areIngredientsMatching(TileEntityAlloyFurnace pulverizer) {
		return firstingredient.containsStackCountMatchOrHigher(pulverizer.getStackInSlot(0)) && secondingredient.containsStackCountMatchOrHigher(pulverizer.getStackInSlot(1)) && thirdingredient.containsStackCountMatchOrHigher(pulverizer.getStackInSlot(2));
	}
	
	public boolean areOutputsMatching(TileEntityAlloyFurnace pulverizer) {
		return output.isItemStackAcceptable(pulverizer.getStackInSlot(3));
	}
	
	public boolean isEnergyMatching(TileEntityAlloyFurnace pulverizer) {
		return pulverizer.getStorage().getEnergyStored() >= energy.getEnergy();
	}
	
	public void execute(TileEntityAlloyFurnace pulverizer) {
		firstingredient.execute(pulverizer, 0);
		secondingredient.execute(pulverizer, 1);
		thirdingredient.execute(pulverizer, 2);
		output.execute(pulverizer, 3);
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
