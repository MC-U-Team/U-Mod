package info.u_team.u_mod.api;

import info.u_team.u_mod.tilentity.machine.TileEntityMachine;

public interface IMachineRecipe {
	
	public boolean areIngredientsMatching(TileEntityMachine machine);
	
	public boolean areOutputsMatching(TileEntityMachine machine);
	
	public boolean isEnergyMatching(TileEntityMachine machine);
	
	public void execute(TileEntityMachine machine);
	
	public int getTime();
	
}
