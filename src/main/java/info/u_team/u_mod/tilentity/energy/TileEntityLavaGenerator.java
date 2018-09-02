package info.u_team.u_mod.tilentity.energy;

import java.util.Map;
import java.util.Map.Entry;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.container.energy.ContainerLavaGenerator;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityLavaGenerator extends TileEntityGeneration implements IClientProgress, IClientFluidTank {
	
	protected int max_progress = 100;
	protected int progress = max_progress;
	
	@SideOnly(Side.CLIENT)
	public int progress_client;
	
	@SideOnly(Side.CLIENT)
	public int fluidtank_client;
	
	@SideOnly(Side.CLIENT)
	public Fluid fluidtype_client;
	
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
		} else if (id == 3) {
			return tank.getFluidAmount();
		} else if (id == 4) {
			@SuppressWarnings("deprecation")
			Map<Fluid, Integer> map = FluidRegistry.getRegisteredFluidIDs();
			if (tank.getFluid() == null) {
				return -1;
			}
			return map.getOrDefault(tank.getFluid().getFluid(), -1);
		}
		return super.getField(id);
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 2) {
			progress_client = value;
		} else if (id == 3) {
			fluidtank_client = value;
		} else if (id == 4) {
			if (value == -1) {
				fluidtype_client = null;
			} else {
				@SuppressWarnings("deprecation")
				Map<Fluid, Integer> map = FluidRegistry.getRegisteredFluidIDs();
				for (Entry<Fluid, Integer> entry : map.entrySet()) {
					if (entry.getValue().equals(value)) {
						fluidtype_client = entry.getKey();
						return;
					}
				}
			}
		} else {
			super.setField(id, value);
		}
	}
	
	@Override
	public int getFieldCount() {
		return 5;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplProgress() {
		return progress_client;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplFluidTank() {
		return fluidtank_client;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Fluid getImplFluid() {
		return fluidtype_client;
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
