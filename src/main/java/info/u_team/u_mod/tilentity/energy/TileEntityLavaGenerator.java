package info.u_team.u_mod.tilentity.energy;

import info.u_team.u_mod.api.IClientProgress;
import info.u_team.u_mod.container.energy.ContainerLavaGenerator;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityLavaGenerator extends TileEntityGeneration implements IClientProgress {
	
	protected int max_progress = 100;
	protected int progress = max_progress;
	
	@SideOnly(Side.CLIENT)
	public int progress_client;
	
	protected FluidTank tank;
	
	public TileEntityLavaGenerator() {
		super(0, "lavagenerator");
		tank = new FluidTank(5000);
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (tank.getFluid() != null && tank.getFluid().getFluid() == FluidRegistry.LAVA && tank.getFluidAmount() >= 100) {
			progress--;
			if (progress <= 0) {
				tank.drainInternal(100, true);
				ienergy.receiveEnergy(1000, false);
				progress = max_progress;
				super.markDirty();
			}
		} else {
			progress = max_progress;
		}
	}
	
	@Override
	public int getField(int id) {
		if (id == 2) {
			return 100 - (int) (((float) progress / (float) max_progress) * 100);
		}
		return super.getField(id);
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 2) {
			progress_client = value;
		} else {
			super.setField(id, value);
		}
	}
	
	@Override
	public int getFieldCount() {
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplProgress() {
		return progress_client;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return new ContainerLavaGenerator(player, world, pos);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		return super.getCapability(capability, facing);
	}
	
}
