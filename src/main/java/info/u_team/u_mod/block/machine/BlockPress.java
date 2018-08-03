package info.u_team.u_mod.block.machine;

import info.u_team.u_mod.block.BlockEnergyGui;
import info.u_team.u_mod.container.machine.ContainerPress;
import info.u_team.u_mod.gui.machine.GuiPress;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.machine.TileEntityPress;
import net.minecraftforge.fml.relauncher.*;

public class BlockPress extends BlockEnergyGui {
	
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
