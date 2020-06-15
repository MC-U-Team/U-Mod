package info.u_team.u_mod.container.basic;

import info.u_team.u_team_core.api.fluid.IFluidHandlerModifiable;
import info.u_team.u_team_core.container.FluidSlot;
import net.minecraftforge.fluids.FluidStack;

public class BasicFluidOutputSlot extends FluidSlot {
	
	public BasicFluidOutputSlot(IFluidHandlerModifiable fluidHandler, int index, int x, int y) {
		super(fluidHandler, index, x, y);
	}
	
	@Override
	public boolean isFluidValid(FluidStack stack) {
		return false;
	}
	
}
