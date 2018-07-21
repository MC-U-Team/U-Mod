package info.u_team.u_mod.tilentity.generation;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.energy.EnergyProvider;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityGeneration extends UTileEntity implements ITickable, ISidedInventory, ICableExceptor, IInteractionObject, IClientEnergy {
	
	protected NonNullList<ItemStack> itemstacks;
	
	protected final EnergyProvider energy;
	
	protected String name;
	
	@SideOnly(Side.CLIENT)
	public int energy_client;
	
	@SideOnly(Side.CLIENT)
	public int progress_client;
	
	public TileEntityGeneration(int size, String name) {
		itemstacks = NonNullList.withSize(size, ItemStack.EMPTY);
		energy = new EnergyProvider(40000, 1000);
		this.name = name;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
		energy.readNBT(compound);
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
		energy.writeNBT(compound);
	}
	
	@Override
	public int getSizeInventory() {
		return itemstacks.size();
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : itemstacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		itemstacks.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return itemstacks.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(itemstacks, index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(itemstacks, index);
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
	public int getField(int id) {
		if (id == 0) {
			return energy.getEnergyStored();
		}
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 0) {
			energy_client = value;
		}
	}
	
	@Override
	public int getFieldCount() {
		return 2;
	}
	
	@Override
	public void clear() {
		itemstacks.clear();
	}
	
	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public IEnergyStorage getStorage() {
		return energy;
	}
	
	@Override
	public String getGuiID() {
		return getName();
	}
	
	@Override
	public boolean takesEnergy() {
		return false;
	}
	
	@Override
	public boolean givesEnergy() {
		return true;
	}
	
	@Override
	public int rate() {
		return energy.getTransfer();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplEnergy() {
		return energy_client;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null) {
			if (facing == EnumFacing.DOWN) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerBottom);
			} else if (facing == EnumFacing.UP) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handlerTop);
			}
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energy);
		}
		return super.getCapability(capability, facing);
	}
}
