package info.u_team.u_mod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class UGuiContainer extends GuiContainer implements IUGui {
	
	public UGuiContainer(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
	
}
