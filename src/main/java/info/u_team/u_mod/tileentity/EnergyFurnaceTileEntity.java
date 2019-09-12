package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.energy.EnergySystem;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyFurnaceTileEntity extends UTileEntity implements ITickable {

	private EnergyStorage storage = new EnergyStorage(10000, 10, 0, 0);

	public EnergyFurnaceTileEntity() {
		super(UModTileEntityTypes.ENERGYFURNANCE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap.equals(CapabilityEnergy.ENERGY))
			return (LazyOptional<T>) LazyOptional.of(() -> storage);
		return super.getCapability(cap, side);
	}	
	
	@Override
	public void tick() {
		this.getCapability(CapabilityEnergy.ENERGY).ifPresent(
				storage -> storage.receiveEnergy(EnergySystem.pullEnergyFromNetwork(getWorld(), getPos(), 10), false));
	}

}
