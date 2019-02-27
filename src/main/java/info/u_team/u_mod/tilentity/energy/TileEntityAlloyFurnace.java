package info.u_team.u_mod.tilentity.energy;

import java.util.List;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.container.energy.ContainerAlloyFurnace;
import info.u_team.u_mod.recipe.RecipeManager;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityAlloyFurnace extends TileEntityMachine {
		
	public TileEntityAlloyFurnace() {
		super(4, "alloyfurnace");
	}
	
	@Override
	public List<IMachineRecipe> getRecipes() {
		return RecipeManager.getAlloyFurnaceRecipes();
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
		return new ContainerAlloyFurnace(player, world, pos);
	}
	
}
