package info.u_team.u_mod.block.energy;

import info.u_team.u_mod.block.BlockEnergyGuiFacing;
import info.u_team.u_mod.container.energy.*;
import info.u_team.u_mod.gui.energy.*;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.energy.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockCoalGenerator extends BlockEnergyGuiFacing {
	
	public BlockCoalGenerator(String name) {
		super(name, TileEntityCoalGenerator.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(ContainerCoalGenerator.class);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(GuiCoalGenerator.class, id);
	}
}
