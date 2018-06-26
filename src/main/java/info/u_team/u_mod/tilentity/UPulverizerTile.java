package info.u_team.u_mod.tilentity;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class UPulverizerTile extends UTileEntity implements ITickable, ISidedInventory {
	
	public static final int MAX_TIME = 100;
	
	private NonNullList<ItemStack> itemstacks = NonNullList.<ItemStack> withSize(4, ItemStack.EMPTY);
	private int max_time = 0;
	private int output_index = -1;
	
	private static ArrayList<ItemStack> input_dictionary = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> primary_output_dictionary = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> secondary_output_dictionary = new ArrayList<ItemStack>();
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
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
		
		if (index == 0 && !flag) {
			this.hasRecipe(stack);
			this.markDirty();
		} else if (index == 0) {
			this.output_index = -1;
		}
	}
	
	public void hasRecipe(ItemStack stack) {
		int i = 0;
		for (ItemStack compare : input_dictionary) {
			if (stack.isItemEqual(compare) && ItemStack.areItemStackTagsEqual(stack, compare) && canCook()) {
				this.max_time = MAX_TIME;
				this.output_index = i;
				return;
			}
			i++;
		}
		this.output_index = -1;
	}
	
	public boolean canCook() {
		ItemStack stack1 = getStackInSlot(1);
		ItemStack stack2 = getStackInSlot(2);
		ItemStack stack3 = getStackInSlot(3);
		
		return ((stack1.isStackable() || stack1.isEmpty()) && stack1.getCount() < stack1.getMaxStackSize()) && ((stack2.isStackable() || stack2.isEmpty()) && stack2.getCount() < stack2.getMaxStackSize()) && ((stack3.isStackable() || stack3.isEmpty()) && stack3.getCount() < stack3.getMaxStackSize());
	}
	
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
		return this.max_time;
	}
	
	@Override
	public void setField(int id, int value) {
		this.max_time = 0;
	}
	
	@Override
	public int getFieldCount() {
		return 1;
	}
	
	@Override
	public void clear() {
		itemstacks.replaceAll(new UnaryOperator<ItemStack>() {
			
			@Override
			public ItemStack apply(ItemStack arg0) {
				return ItemStack.EMPTY;
			}
		});
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
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
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
		if (this.output_index >= 0) {
			this.max_time--;
			if (this.max_time <= 0) {
				ItemStack stack_primary_out = itemstacks.get(3);
				ItemStack input = itemstacks.get(0);
				ItemStack out_primary = primary_output_dictionary.get(output_index);
				if (!stack_primary_out.isEmpty()) {
					stack_primary_out.setCount(stack_primary_out.getCount() + 1);
				} else {
					itemstacks.set(3, out_primary);
				}
				if (this.canCook() && !input.isEmpty()) {
					this.max_time = MAX_TIME;
				}
			}
		}
	}
	
}
