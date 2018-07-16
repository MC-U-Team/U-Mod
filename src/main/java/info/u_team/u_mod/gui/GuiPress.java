package info.u_team.u_mod.gui;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.*;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_mod.tilentity.pulverizer.TileEntityPulverizer;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class GuiPress extends UGuiContainer {
		
	public GuiPress(UContainer inventorySlotsIn) {
		super((ContainerBase) inventorySlotsIn);
		this.setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/press.png"));
	}
	
	@Override
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
		if (tab == EnumModeTab.NORMAL)
			this.drawTexturedModalRect(x_offset + 47, y_offset + 28, 0, 166, Math.round(64 * (100 - getContainer().tile.getField(0)) / 100), 7);
	}
	
}
