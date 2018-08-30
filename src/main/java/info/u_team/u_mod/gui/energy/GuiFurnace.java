package info.u_team.u_mod.gui.energy;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IClientProgress;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.gui.UGuiContainer;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends UGuiContainer {
	
	public GuiFurnace(UContainer inventorySlotsIn) {
		super((ContainerBase) inventorySlotsIn);
		setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/furnace.png"));
	}
	
	@Override
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
		if (tab == EnumModeTab.NORMAL) {
			IClientProgress iclient = (IClientProgress) getContainer().tile;
			drawTexturedModalRect(x_offset + 47, y_offset + 28, 0, 166, Math.round(64 * iclient.getImplProgress() / 100), 7);
		}
	}
}
