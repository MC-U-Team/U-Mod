package info.u_team.u_mod.tilentity;

import static info.u_team.u_mod.recipe.RecipeManager.getPulverizerRecipes;

import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_mod.container.ContainerPulverizer;
import info.u_team.u_mod.recipe.RecipePulverizer;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityPulverizer extends UTileEntity implements ITickable, ISidedInventory, ICableExceptor, IInteractionObject {
	
	public static final int MAX_TIME = 100;
	public static final int ENERGY_CONSUMED = 100;
	
	private NonNullList<ItemStack> itemstacks = NonNullList.withSize(4, ItemStack.EMPTY);
	private int time_left = MAX_TIME;
	
	// private int output_index = -1;
	private int recipe_id = -1;
	private RecipePulverizer recipe;
	
	public int impl_energy;
	
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY;
	
	private final IEnergyStorage energy;
	
	public TileEntityPulverizer() {
		energy = ENERGY.getDefaultInstance();
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
		if (compound.hasKey("energy")) {
			ENERGY.readNBT(energy, null, compound.getTag("energy"));
		}
		this.time_left = compound.getInteger("time");
		this.recipe_id = compound.getInteger("recipeid");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
		compound.setTag("energy", ENERGY.writeNBT(energy, null));
		compound.setInteger("time", this.time_left);
		compound.setInteger("recipeid", this.recipe_id);
		this.recipe = getPulverizerRecipes().get(this.recipe_id);
	}
	
	@Override
	public String getName() {
		return "pulverizer";
	}
	
	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public int getSizeInventory() {
		return this.itemstacks.size();
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.itemstacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.itemstacks.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.itemstacks, index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.itemstacks, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.itemstacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.itemstacks.set(index, stack);
		
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if (flag && index == 0) {
			this.recipe_id = -1;
			this.time_left = MAX_TIME;
		}
		
		if (index == 0 || this.recipe_id < 0) {
			this.hasRecipe();
			this.markDirty();
		}
	}
	
	public void hasRecipe() {
		int i = 0;
		for (RecipePulverizer recipe : getPulverizerRecipes()) {
			if (recipe.areIngredientsMatching(this)) {
				if (recipe.areOutputsMatching(this)) {
					this.recipe = recipe;
					this.recipe_id = i;
					this.time_left = MAX_TIME;
				}
				return;
			}
			i++;
		}
		this.recipe_id = -1;
	}
	
	// public boolean canCook(int index) {
	// ItemStack stack1 = getStackInSlot(1);
	// ItemStack stack2 = getStackInSlot(2);
	// ItemStack stack3 = getStackInSlot(3);
	// InputStack in = input_dictionary.get(index);
	//
	// return ((stack1.isStackable() || stack1.isEmpty()) && stack1.getCount() +
	// in.getCount() <= stack1.getMaxStackSize()) && ((stack2.isStackable() ||
	// stack2.isEmpty()) && stack2.getCount() + in.getCount() <=
	// stack2.getMaxStackSize()) && ((stack3.isStackable() || stack3.isEmpty()) &&
	// stack3.getCount() + in.getCount() <= stack3.getMaxStackSize());
	// }
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
	}
	
	@Override
	public void closeInventory(EntityPlayer player) {
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int getField(int id) {
		if (id == 0) {
			return this.time_left;
		} else if (id == 1) {
			return this.energy.getEnergyStored();
		} else {
			return 0;
		}
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 0) {
			this.time_left = value;
		} else if (id == 1) {
			this.impl_energy = value;
		}
	}
	
	@Override
	public int getFieldCount() {
		return 2;
	}
	
	@Override
	public void clear() {
		itemstacks.replaceAll(stack -> ItemStack.EMPTY);
	}
	
	public static final int[] OUT = { 1, 2, 3 }, IN = { 0 };
	
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
	public void update() {
		if (!world.isRemote) {
			if (ENERGY_CONSUMED > energy.getEnergyStored())
				return;
			if (this.recipe_id >= 0 && recipe != null) {
				this.time_left--;
				if (this.time_left <= 0) {
					recipe.executeRecipe(this);
					energy.extractEnergy(ENERGY_CONSUMED, false);
					this.markDirty();
					if (recipe.areOutputsMatching(this) && !itemstacks.get(0).isEmpty()) {
						this.time_left = MAX_TIME;
					} else {
						this.recipe_id = -1;
					}
				}
			}
		}
	}
	
	@Override
	public IEnergyStorage getStorage() {
		return this.energy;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerPulverizer(playerIn, this.world, this.pos);
	}
	
	@Override
	public String getGuiID() {
		return getName();
	}
	
	@Override
	public boolean takesEnergy() {
		return true;
	}
	
	@Override
	public boolean givesEnergy() {
		return false;
	}
	
	@Override
	public int rate() {
		return 5;
	}
	
}
