package info.u_team.u_mod.tilentity.energy;

import info.u_team.u_mod.api.*;
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

public abstract class TileEntityEnergyGui extends UTileEntity implements ITickable, ISidedInventory, ICableExceptor, IInteractionObject, IClientEnergy {
	
	protected NonNullList<ItemStack> itemstacks;
	
	protected String name;
	
	protected IEnergyTile ienergy;
	
	@SideOnly(Side.CLIENT)
	public int energy_client;
	
	@SideOnly(Side.CLIENT)
	public int maxenergy_client;
	
	public TileEntityEnergyGui(int size, String name, IEnergyTile ienergy) {
		itemstacks = NonNullList.withSize(size, ItemStack.EMPTY);
		this.name = name;
		this.ienergy = ienergy;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
		ienergy.readNBT(compound);
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
		ienergy.writeNBT(compound);
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
			return ienergy.getEnergyStored();
		} else if (id == 1) {
			return ienergy.getMaxEnergyStored();
		}
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 0) {
			energy_client = value;
		} else if (id == 1) {
			maxenergy_client = value;
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
		return ienergy;
	}
	
	@Override
	public String getGuiID() {
		return getName();
	}
	
	@Override
	public boolean takesEnergy(EnumFacing face) {
		return ienergy.canReceive();
	}
	
	@Override
	public boolean givesEnergy(EnumFacing face) {
		return ienergy.canExtract();
	}
	
	@Override
	public int rate() {
		return ienergy.getTransfer();
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplMaxEnergy() {
		return maxenergy_client;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(ienergy);
		}
		return super.getCapability(capability, facing);
	}
}
