package info.u_team.u_mod.tilentity.machine;

import static info.u_team.u_mod.recipe.RecipeManager.getPressRecipes;

import info.u_team.u_mod.container.machine.ContainerPress;
import info.u_team.u_mod.container.machine.ContainerPulverizer;
import info.u_team.u_mod.recipe.machine.RecipePress;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityPress extends TileEntityMachine {
	
	public static final int[] IN = { 0 }, OUT = { 1 };
	
	public TileEntityPress() {
		super(2, "press");
	}
	
	@Override
	public void checkRecipe() {
		for (int i = 0; i < getPressRecipes().size(); i++) {
			RecipePress recipe = getPressRecipes().get(i);
			if (recipe.areIngredientsMatching(this)) {
				recipeid = i;
				progress = max_progress = recipe.getTime();
				return;
			}
		}
		progress = max_progress = 100;
		recipeid = -1;
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (recipeid >= 0) {
			RecipePress recipe = getPressRecipes().get(recipeid);
			if (!recipe.areIngredientsMatching(this)) {
				recipeid = -1;
				return;
			}
			if (!recipe.isEnergyMatching(this) || !recipe.areOutputsMatching(this)) {
				return;
			}
			progress--;
			if (progress <= 0) {
				recipe.execute(this);
				progress = max_progress;
				super.markDirty();
			}
		}
	}
	
	@Override
	public int rate() {
		return 5;
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
		return new ContainerPress(player, world, pos);
	}
	
}
