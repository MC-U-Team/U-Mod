package info.u_team.u_mod.block.machine;

import info.u_team.u_mod.block.BlockEnergyGui;
import info.u_team.u_mod.container.machine.*;
import info.u_team.u_mod.gui.machine.*;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.machine.TileEntityEnricher;
import net.minecraftforge.fml.relauncher.*;

public class BlockEnricher extends BlockEnergyGui {
	
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
