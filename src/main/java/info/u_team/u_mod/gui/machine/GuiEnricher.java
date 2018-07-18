package info.u_team.u_mod.gui.machine;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IClientProgress;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.gui.UGuiContainer;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class GuiEnricher extends UGuiContainer {
	
	public GuiEnricher(UContainer inventorySlotsIn) {
		super((ContainerBase) inventorySlotsIn);
		this.setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/enricher.png"));
	}
	
	@Override
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
		if (tab == EnumModeTab.NORMAL) {
			IClientProgress iclient = (IClientProgress) getContainer().tile;
			drawTexturedModalRect(x_offset + 61, y_offset + 32, 0, 166, Math.round(53 * iclient.getImplProgress() / 100), 16);
		}
	}
}
