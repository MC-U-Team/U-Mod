package info.u_team.u_mod.tilentity.energy;

import java.util.List;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.container.machine.ContainerAlloyFurnace;
import info.u_team.u_mod.recipe.RecipeManager;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityAlloyFurnace extends TileEntityMachine {
	
	public static final int[] IN = { 0, 1, 2 }, OUT = { 3 };
	
	public TileEntityAlloyFurnace() {
		super(4, "alloyfurnace");
	}
	
	@Override
	public List<IMachineRecipe> getRecipes() {
		return RecipeManager.getAlloyFurnaceRecipes();
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return OUT;
		} else if (side == EnumFacing.UP) {
			return IN;
		}
		return null;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		if (index == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (index > 0) {
			return true;
		}
		return false;
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
