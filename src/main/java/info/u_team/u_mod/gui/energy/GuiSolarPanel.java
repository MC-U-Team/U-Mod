package info.u_team.u_mod.gui.energy;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.gui.UGuiContainer;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class GuiSolarPanel extends UGuiContainer {
	
	public GuiSolarPanel(UContainer container) {
		super((ContainerBase) container);
		setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/solar_panel.png"));
	}
}
