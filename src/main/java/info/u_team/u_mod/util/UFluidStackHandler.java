package info.u_team.u_mod.util;

import net.minecraft.nbt.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class UFluidStackHandler implements IFluidHandler, INBTSerializable<CompoundNBT> {
	
	protected final NonNullList<FluidStack> stacks;
	
	public UFluidStackHandler(int size) {
		stacks = NonNullList.withSize(size, FluidStack.EMPTY);
	}
	
	@Override
	public int getTanks() {
		return 0;
	}
	
	@Override
	public FluidStack getFluidInTank(int tank) {
		return null;
	}
	
	@Override
	public int getTankCapacity(int tank) {
		return 0;
	}
	
	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return false;
	}
	
	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return 0;
	}
	
	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		return null;
	}
	
	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return null;
	}
	
	protected void onLoad() {
	}
	
	protected void onContentsChanged(int slot) {
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		final CompoundNBT compound = new CompoundNBT();
		final ListNBT list = new ListNBT();
		
		for (int index = 0; index < stacks.size(); index++) {
			final FluidStack fluidStack = stacks.get(index);
			if (!fluidStack.isEmpty()) {
				final CompoundNBT slotCompound = new CompoundNBT();
				slotCompound.putByte("Slot", (byte) index);
				fluidStack.writeToNBT(slotCompound);
				list.add(slotCompound);
			}
		}
		
		if (!list.isEmpty()) {
			compound.put("Fluids", list);
		}
		
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		final ListNBT list = compound.getList("Fluids", 10);
		
		for (int index = 0; index < list.size(); index++) {
			final CompoundNBT slotCompound = list.getCompound(index);
			final int slot = slotCompound.getByte("Slot") & 255;
			if (slot >= 0 && slot < list.size()) {
				stacks.set(slot, FluidStack.loadFluidStackFromNBT(slotCompound));
			}
		}
		onLoad();
	}
	
}
