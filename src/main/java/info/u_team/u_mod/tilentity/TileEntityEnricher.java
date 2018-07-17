package info.u_team.u_mod.tilentity;

import static info.u_team.u_mod.recipe.RecipeManager.getPulverizerRecipes;

import info.u_team.u_mod.container.ContainerPulverizer;
import info.u_team.u_mod.recipe.machine.RecipePulverizer;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityEnricher extends TileEntityMachine {
	
	public static final int MAX_TIME = 100;
	public static final int ENERGY_CONSUMED = 100;
	
	public static final int[] OUT = { 1 }, IN = { 0 };
	
	private int progress = MAX_TIME;
	private int recipe = -1;
		
	public TileEntityEnricher() {
		super(2, "enricher");
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) { // METHOD will only be executed when you set stacks, not when you increase them.
																		// So it wont be updated all the time
		ItemStack itemstack = itemstacks.get(index);
		itemstacks.set(index, stack);
		
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if (index == 0) {
			boolean flag = stack.isEmpty() && !stack.isItemEqual(itemstack) || !ItemStack.areItemStackTagsEqual(stack, itemstack);
			if (flag) {
				recipe = -1;
				progress = MAX_TIME;
			}
		}
		
		if (index == 0 || recipe < 0) {
			searchRecipe();
		}
		markDirty();
	}
	
	public void searchRecipe() {
		int i = 0;
		for (RecipePulverizer recipe : getPulverizerRecipes()) {
			if (recipe.areIngredientsMatching(this)) {
				if (recipe.areOutputsMatching(this)) {
					this.recipe = i;
					this.progress = MAX_TIME;
				}
				return;
			}
			i++;
		}
		this.recipe = -1;
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (ENERGY_CONSUMED > energy.getEnergyStored())
			return;
		if (this.recipe >= 0) {
			this.progress--;
			if (this.progress <= 0) {
			}
		}
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		super.readNBT(compound);
		progress = compound.getInteger("progress");
		recipe = compound.getInteger("recipe");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		super.writeNBT(compound);
		compound.setInteger("progress", progress);
		compound.setInteger("recipe", recipe);
	}
	
	@Override
	public void setField(int id, int value) {
		super.setField(id, value);
		if (id == 1) {
			progress = value;
		}
	}
	
	@Override
	public int getField(int id) {
		if (id == 0) {
			return super.getField(id);
		} else if (id == 1) {
			return this.progress;
		}
		return 0;
	}
	
	@Override
	public int getFieldCount() {
		return 2;
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
		return new ContainerPulverizer(player, world, pos);
	}
	
}
