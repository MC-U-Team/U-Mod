package info.u_team.u_mod.api;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.*;

public interface IClientFluidTank {
	
	@SideOnly(Side.CLIENT)
	int getImplFluidTank();
	
	@SideOnly(Side.CLIENT)
	Fluid getImplFluid();
}
