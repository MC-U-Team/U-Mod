package info.u_team.u_mod.block.machine;

import info.u_team.u_mod.block.BlockEnergyGui;
import info.u_team.u_mod.container.machine.ContainerAlloyFurnace;
import info.u_team.u_mod.gui.machine.GuiAlloyFurnace;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.tilentity.machine.TileEntityAlloyFurnace;
import net.minecraftforge.fml.relauncher.*;

public class BlockAlloyFurnace extends BlockEnergyGui {
	
	public BlockAlloyFurnace(String name) {
		super(name, TileEntityAlloyFurnace.class);
	}
	
	@Override
	protected int getContainer() {
		return UGuis.addContainer(ContainerAlloyFurnace.class);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void getGui(int id) {
		UGuis.addGuiContainer(GuiAlloyFurnace.class, id);
	}
	
}
