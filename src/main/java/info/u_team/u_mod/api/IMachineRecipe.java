package info.u_team.u_mod.api;

import net.minecraft.inventory.IInventory;

public interface IMachineRecipe {
	
	public boolean areIngredientsMatching(IInventory inventory);
	
	public boolean areOutputsMatching(IInventory inventory);
	
	public void executeRecipe(IInventory inventory);
	
}
