package info.u_team.u_mod.tilentity;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_mod.container.UContainerPulverizer;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.IEnergyStorage;

public class UTileEntityPulverizer extends UTileEntity implements ITickable, ISidedInventory, ICableExceptor, IInteractionObject {
	
	public static final int MAX_TIME = 100;
	public static final int ENERGY_CONSUMED = 100;
	
	private NonNullList<ItemStack> itemstacks = NonNullList.withSize(4, ItemStack.EMPTY);
	private int time_left = MAX_TIME;
	private int output_index = -1;
	
	private static ArrayList<ItemStack> input_dictionary = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> primary_output_dictionary = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> secondary_output_dictionary = new ArrayList<ItemStack>();
	
	public static void addRecipe(ItemStack input, ItemStack output, @Nullable ItemStack second_output) {
		input_dictionary.add(input);
		primary_output_dictionary.add(output);
		secondary_output_dictionary.add(second_output);
	}
	
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY;
	
	private final IEnergyStorage energy;
	
	public UTileEntityPulverizer() {
		energy = ENERGY.getDefaultInstance();
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
		if (compound.hasKey("energy")) {
			ENERGY.readNBT(energy, null, compound.getTag("energy"));
		}
		this.time_left = compound.getInteger("time");
		this.output_index = compound.getInteger("output");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
		compound.setTag("energy", ENERGY.writeNBT(energy, null));
		compound.setInteger("time", this.time_left);
		compound.setInteger("output", this.output_index);
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
			this.output_index = -1;
			this.time_left = MAX_TIME;
		}
		
		if (index == 0 || this.output_index < 0) {
			this.hasRecipe(getStackInSlot(0));
			this.markDirty();
		}
	}
	
	public void hasRecipe(ItemStack stack) {
		int i = 0;
		for (ItemStack compare : input_dictionary) {
			if (compare.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, compare)) {
				if (canCook(i)) {
					this.time_left = MAX_TIME;
					this.output_index = i;
				}
				return;
			}
			i++;
		}
		this.output_index = -1;
	}
	
	public boolean canCook(int index) {
		ItemStack stack1 = getStackInSlot(1);
		ItemStack stack2 = getStackInSlot(2);
		ItemStack stack3 = getStackInSlot(3);
		ItemStack in = input_dictionary.get(index);
		
		return ((stack1.isStackable() || stack1.isEmpty()) && stack1.getCount() + in.getCount() <= stack1.getMaxStackSize()) && ((stack2.isStackable() || stack2.isEmpty()) && stack2.getCount() + in.getCount() <= stack2.getMaxStackSize()) && ((stack3.isStackable() || stack3.isEmpty()) && stack3.getCount() + in.getCount() <= stack3.getMaxStackSize());
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
		return this.time_left;
	}
	
	@Override
	public void setField(int id, int value) {
		this.time_left = value;
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
		if (!world.isRemote) {
			if (ENERGY_CONSUMED > energy.getEnergyStored())
				return;
			if (this.output_index >= 0) {
				this.time_left--;
				if (this.time_left <= 0) {
					ItemStack stack_primary_out = itemstacks.get(3);
					ItemStack out_primary = primary_output_dictionary.get(output_index);
					if (!stack_primary_out.isEmpty()) {
						stack_primary_out.grow(out_primary.getCount());
					} else {
						itemstacks.set(3, out_primary.copy());
					}
					
					ItemStack out_second = secondary_output_dictionary.get(output_index);
					if (out_second != null) {
						ItemStack stack_second_out = itemstacks.get(2);
						if (!stack_second_out.isEmpty()) {
							stack_second_out.grow(out_second.getCount());
						} else {
							itemstacks.set(2, out_second.copy());
						}
					}
					
					ItemStack input = itemstacks.get(0);
					input.shrink(1);
					energy.extractEnergy(ENERGY_CONSUMED, false);
					this.markDirty();
					if (this.canCook(this.output_index) && !input.isEmpty()) {
						this.time_left = MAX_TIME;
					} else {
						this.output_index = -1;
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
		return new UContainerPulverizer(playerIn, this.world, this.pos);
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
