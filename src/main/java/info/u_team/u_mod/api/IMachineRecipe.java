package info.u_team.u_mod.api;

import info.u_team.u_mod.util.NonNullSlot;

public interface IMachineRecipe {
	
	public NonNullSlot<IRecipeSlot> getInput();
	
	public NonNullSlot<IRecipeSlot> getOutput();
	
}
