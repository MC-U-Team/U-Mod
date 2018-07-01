package info.u_team.u_mod.gui;

import info.u_team.u_mod.api.IUGui;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class UGuiContainer extends GuiContainer implements IUGui {
	
	protected ResourceLocation BACKGROUND;
	
	public UGuiContainer(UContainer inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(BACKGROUND);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
	
}
