package info.u_team.u_mod.api;

import java.util.EnumMap;
import java.util.concurrent.Callable;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;


public enum CapabilityIOMode implements IStorage<IIOMode>, Callable<IIOMode>{

	INSTANCE;
	
	@CapabilityInject(IIOMode.class)
	public static Capability<IIOMode> IOMODE_CAPABILITY;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IIOMode.class, INSTANCE, INSTANCE);
	}
	
	@Override
	public NBTBase writeNBT(Capability<IIOMode> capability, IIOMode instance, EnumFacing side) {
		NBTTagCompound tagcomp = new NBTTagCompound();
		tagcomp.setBoolean("input", instance.isInput(side));
		tagcomp.setIntArray("slots", instance.getSlots(side));
		return tagcomp;
	}

	@Override
	public void readNBT(Capability<IIOMode> capability, IIOMode instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound comp = (NBTTagCompound)nbt;
		instance.setSlotForFacing(side, comp.getIntArray("slots"), comp.getBoolean("input"));
	}

	@Override
	public IIOMode call() throws Exception {
		return new IIOMode.IOModeHandler(null);
	}
	
}
