package info.u_team.u_mod.block.energy;

import info.u_team.u_mod.block.BlockEnergyGuiFacing;
import info.u_team.u_mod.container.energy.ContainerPress;
import info.u_team.u_mod.gui.energy.GuiPress;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.energy.TileEntityPress;
import net.minecraftforge.fml.relauncher.*;

public class BlockPress extends BlockEnergyGuiFacing {
	
	public BlockPress(String name) {
		super(name, TileEntityPress.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(ContainerPress.class);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(GuiPress.class, id);
	}
	
}
