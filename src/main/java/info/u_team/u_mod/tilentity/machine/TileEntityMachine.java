package info.u_team.u_mod.tilentity.machine;

import java.util.List;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.api.IIOMode.IOModeHandler;
import info.u_team.u_mod.energy.EnergyConsumer;
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

public abstract class TileEntityMachine extends UTileEntity implements ITickable,
ISidedInventory, ICableExceptor, IInteractionObject, IClientEnergy, IClientProgress {
	
	protected NonNullList<ItemStack> itemstacks;
	
	protected final EnergyConsumer energy;
	protected final IOModeHandler iomode_handler;
	
	protected int max_progress = 100;
	protected int progress = max_progress;
	
	protected int recipeid = -1;
	
	protected String name;
	
	@SideOnly(Side.CLIENT)
	public int energy_client;
	
	@SideOnly(Side.CLIENT)
	public int progress_client;
	
	public TileEntityMachine(int size, String name) {
		itemstacks = NonNullList.withSize(size, ItemStack.EMPTY);
		energy = new EnergyConsumer(40000, 1000);
		this.iomode_handler = new IOModeHandler(this);
		this.name = name;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return this.iomode_handler.getSlots(side);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return this.iomode_handler.canExtractItem(direction, index, stack);
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.iomode_handler.canInsertItem(direction, index, itemStackIn);
	}
	
	public void checkRecipe() {
		for (int i = 0; i < getRecipes().size(); i++) {
			IMachineRecipe recipe = getRecipes().get(i);
			if (recipe.areIngredientsMatching(this)) {
				recipeid = i;
				if (max_progress != recipe.getTime()) {
					progress = max_progress = recipe.getTime();
				} else {
					max_progress = recipe.getTime();
				}
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
			IMachineRecipe recipe = getRecipes().get(recipeid);
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
	public void markDirty() {
		if (!world.isRemote) {
			checkRecipe();
		}
		super.markDirty();
	}
	
	public abstract List<IMachineRecipe> getRecipes();
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound, itemstacks);
		energy.readNBT(compound);
		progress = compound.getInteger("progress");
		recipeid = compound.getInteger("recipe");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, itemstacks);
		energy.writeNBT(compound);
		compound.setInteger("progress", progress);
		compound.setInteger("recipe", recipeid);
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
		} else if (id == 1) {
			return 100 - (int) (((float) progress / (float) max_progress) * 100);
		}
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 0) {
			energy_client = value;
		} else if (id == 1) {
			progress_client = value;
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
	public boolean takesEnergy(EnumFacing face) {
		return true;
	}
	
	@Override
	public boolean givesEnergy(EnumFacing face) {
		return false;
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplProgress() {
		return progress_client;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY
				|| capability == CapabilityIOMode.IOMODE_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
		
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.iomode_handler.get(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energy);
		} else if (capability == CapabilityIOMode.IOMODE_CAPABILITY) {
			return CapabilityIOMode.IOMODE_CAPABILITY.cast(this.iomode_handler);
		}
		return super.getCapability(capability, facing);
	}
}
