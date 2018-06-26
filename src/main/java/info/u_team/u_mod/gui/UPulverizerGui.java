package info.u_team.u_mod.gui;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class UPulverizerGui extends UGuiContainer {
	
	public UPulverizerGui(UContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		BACKGROUND = new ResourceLocation(UConstants.MODID, "textures/gui/pulverizer.png");
	}
	
}
