package info.u_team.u_mod.tilentity.machine;

import java.util.List;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.container.machine.ContainerFurnace;
import info.u_team.u_mod.recipe.RecipeManager;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityFurnace extends TileEntityMachine {
	
	public static final int[] IN = { 0 }, OUT = { 1 };
	
	public TileEntityFurnace() {
		super(2, "furnace");
	}
	
	@Override
	public List<IMachineRecipe> getRecipes() {
		return RecipeManager.getFurnaceRecipes();
	}
		
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
		return new ContainerFurnace(player, world, pos);
	}
	
}
