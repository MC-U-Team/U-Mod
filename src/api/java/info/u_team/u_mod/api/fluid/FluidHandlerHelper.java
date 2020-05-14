package info.u_team.u_mod.api.fluid;

import net.minecraftforge.fluids.FluidStack;

public class FluidHandlerHelper {
	
	public static boolean canFluidStacksStack(FluidStack a, FluidStack b) {
		if (a.isEmpty() || !(a.getFluid() == b.getFluid()) || a.hasTag() != b.hasTag())
			return false;
		
		return (!a.hasTag() || a.getTag().equals(b.getTag()));
	}
	
}
