package info.u_team.u_mod.tilentity;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.container.ContainerFurnace;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityPress extends UTileEntity implements ISidedInventory, IClientEnergy, IInteractionObject, ITickable, ICableExceptor {
	
	public static final int MAX_TIME = 100;
	public static final int ENERGY_CONSUMED = 100;
	
	private NonNullList<ItemStack> itemstacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private int time_left = MAX_TIME;
	private int output_index = -1;
	
	public int impl_energy;
	
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY;
	
	private final IEnergyStorage energy;
	
	public TileEntityPress() {
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
		return "press";
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
			this.hasRecipe();
			this.markDirty();
		}
	}
	
	private void hasRecipe() {
		// TODO Check recipe
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
	
	public static final int[] OUT = { 1 }, IN = { 0 };
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return OUT;
		} else if (side == EnumFacing.UP) {
			return IN;
		}
		return new int[0];
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
	public IEnergyStorage getStorage() {
		return this.energy;
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
		return 2;
	}
	
	@Override
	public void tick() {
		if (!world.isRemote) {
			if (ENERGY_CONSUMED > energy.getEnergyStored())
				return;
			if (this.output_index >= 0) {
				this.time_left--;
				if (this.time_left <= 0) {
					ItemStack input = itemstacks.get(0);
					ItemStack output = itemstacks.get(1);
					// TODO Added cook logic
				}
			}
		}
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerFurnace(playerIn, world, pos);
	}
	
	@Override
	public String getGuiID() {
		return "press";
	}
	
	@Override
	public int getImpl() {
		return this.impl_energy;
	}
	
}
