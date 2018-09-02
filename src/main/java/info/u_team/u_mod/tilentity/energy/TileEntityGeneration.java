package info.u_team.u_mod.tilentity.energy;

import info.u_team.u_mod.energy.EnergyProvider;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityGeneration extends TileEntityEnergyGui {
	
	public TileEntityGeneration(int size, String name) {
		super(size, name, new EnergyProvider(40000, 1000));
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
			return CapabilityEnergy.ENERGY.cast(ienergy);
		}
		return super.getCapability(capability, facing);
	}
}
