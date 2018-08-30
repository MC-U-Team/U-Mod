package info.u_team.u_mod.block.energy;

import info.u_team.u_mod.block.BlockEnergyGuiFacing;
import info.u_team.u_mod.container.energy.ContainerPulverizer;
import info.u_team.u_mod.gui.energy.GuiPulverizer;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.energy.TileEntityPulverizer;
import net.minecraftforge.fml.relauncher.*;

public class BlockPulveriser extends BlockEnergyGuiFacing {
	
	public BlockPulveriser(String name) {
		super(name, TileEntityPulverizer.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(ContainerPulverizer.class);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(GuiPulverizer.class, id);
	}
	
}
