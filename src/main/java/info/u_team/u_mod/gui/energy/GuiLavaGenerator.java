package info.u_team.u_mod.gui.energy;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IClientProgress;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.gui.UGuiContainer;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class GuiLavaGenerator extends UGuiContainer {
	
	public GuiLavaGenerator(UContainer container) {
		super((ContainerBase) container);
		setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/coal_generator.png"));
	}
	
	@Override
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
		if (tab == EnumModeTab.NORMAL) {
			IClientProgress iclient = (IClientProgress) getContainer().tile;
			drawTexturedModalRect(x_offset + 81 + 14, y_offset + 55 + 14, 176 + 14, 0 + 14, -14, -1 * Math.round(14 * iclient.getImplProgress() / 100));
		}
	}
}
