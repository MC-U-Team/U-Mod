package info.u_team.u_mod.gui;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.container.ContainerPulverizer;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_mod.tilentity.pulverizer.TileEntityPulverizer;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class GuiPulverizer extends UGuiContainer {
	
	private TileEntityPulverizer tile;
	
	public GuiPulverizer(UContainer inventorySlotsIn) {
		super((ContainerBase) inventorySlotsIn);
		ContainerPulverizer container = ((ContainerPulverizer) this.inventorySlots);
		tile = (TileEntityPulverizer) container.world.getTileEntity(container.pos);
		
		this.setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/pulverizer.png"));
	}
	
	@Override
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
		if(tab == EnumModeTab.NORMAL)
			this.drawTexturedModalRect(x_offset + 47, y_offset + 28, 0, 166, Math.round(64 * (100 - tile.getField(0)) / 100), 7);
		if(tab == EnumModeTab.ENERGY)
			this.drawTexturedModalRect(x_offset + 9, y_offset + 8, 0, 166, Math.round((float)158 * (float)((float)tile.impl_energy / (float)tile.getStorage().getMaxEnergyStored())), 20);
	}
	
}