package info.u_team.u_mod.block.energy;

import info.u_team.u_mod.block.BlockEnergyGuiFacing;
import info.u_team.u_mod.container.machine.ContainerEnricher;
import info.u_team.u_mod.gui.machine.GuiEnricher;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.energy.TileEntityEnricher;
import net.minecraftforge.fml.relauncher.*;

public class BlockEnricher extends BlockEnergyGuiFacing {
	
	public BlockEnricher(String name) {
		super(name, TileEntityEnricher.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(ContainerEnricher.class);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(GuiEnricher.class, id);
	}
	
}
