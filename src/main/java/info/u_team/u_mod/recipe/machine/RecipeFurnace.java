package info.u_team.u_mod.recipe.machine;

import java.util.*;

import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.tilentity.machine.TileEntityFurnace;
import net.minecraft.item.ItemStack;

public class RecipeFurnace {
	
	private List<List<ItemStack>> jeiitemingredients;
	private List<ItemStack> jeiitemoutputs;
	
	private IngredientItemStack ingredient;
	private OutputItemStack output;
	private ProcessEnergy energy;
	private ProcessTime time;
	
	public RecipeFurnace(IngredientItemStack ingredient, OutputItemStack output, ProcessEnergy energy, ProcessTime time) {
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
	
	public boolean areIngredientsMatching(TileEntityFurnace pulverizer) {
		return ingredient.containsStackCountMatchOrHigher(pulverizer.getStackInSlot(0));
	}
	
	public boolean areOutputsMatching(TileEntityFurnace pulverizer) {
		return output.isItemStackAcceptable(pulverizer.getStackInSlot(1));
	}
	
	public boolean isEnergyMatching(TileEntityFurnace pulverizer) {
		return pulverizer.getStorage().getEnergyStored() >= energy.getEnergy();
	}
	
	public void execute(TileEntityFurnace pulverizer) {
		ingredient.execute(pulverizer, 0);
		output.execute(pulverizer, 1);
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
