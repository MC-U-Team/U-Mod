package info.u_team.u_mod.block.machine;

import info.u_team.u_mod.block.BlockEnergyGui;
import info.u_team.u_mod.container.machine.ContainerPulverizer;
import info.u_team.u_mod.gui.machine.GuiPulverizer;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.machine.TileEntityPulverizer;
import net.minecraftforge.fml.relauncher.*;

public class BlockPulveriser extends BlockEnergyGui {
	
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
