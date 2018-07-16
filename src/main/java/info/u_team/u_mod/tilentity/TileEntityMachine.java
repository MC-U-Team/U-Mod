package info.u_team.u_mod.tilentity;

import info.u_team.u_mod.api.*;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.*;

public abstract class TileEntityMachine extends UTileEntity implements ITickable, ISidedInventory, ICableExceptor, IInteractionObject, IClientEnergy {
	
	protected NonNullList<ItemStack> itemstacks;
	
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY;
	
	protected final IEnergyStorage energy;
	
	protected String name;
	
	@SideOnly(Side.CLIENT)
	public int energy_client;
	
	public TileEntityMachine(int size, String name) {
		energy = ENERGY.getDefaultInstance();
		itemstacks = NonNullList.withSize(size, ItemStack.EMPTY);
		this.name = name;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
		if (compound.hasKey("energy")) {
			ENERGY.readNBT(energy, null, compound.getTag("energy"));
		}
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
		compound.setTag("energy", ENERGY.writeNBT(energy, null));
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
		return 1;
	}
	
	@Override
	public void clear() {
		itemstacks.replaceAll(stack -> ItemStack.EMPTY);
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
		return true;
	}
	
	@Override
	public boolean givesEnergy() {
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImpl() {
		return energy_client;
	}
}
