package info.u_team.u_mod.gui;

import java.io.IOException;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.IUGui;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UGuiContainer extends GuiContainer implements IUGui {
	
	private static final ResourceLocation ENERGY = new ResourceLocation(UConstants.MODID, "textures/gui/energy.png");
	private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	
	private ResourceLocation normal_background;
	private ResourceLocation used_background;
	private EnumModeTab tab = EnumModeTab.NORMAL;
	
	public UGuiContainer(UContainer inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(used_background);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		this.drawInBackground(tab, mouseX, mouseY, i, j);
	}
	
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		drawTabs(mouseX, mouseY);
		if (tab == EnumModeTab.NORMAL) {
			super.drawScreen(mouseX, mouseY, partialTicks);
		} else if (tab == EnumModeTab.ENERGY) {
			this.drawEnergyTab(partialTicks, mouseX, mouseY);
		}
		drawForgroundTab();
		drawOverlay(mouseX, mouseY);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	private void drawOverlay(int mouseX, int mouseY) {
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		
		for (EnumModeTab ttab : EnumModeTab.values()) {
			if (mouseX > i + 28 * ttab.ordinal() && mouseX < i + 28 * ttab.ordinal() + 28 && mouseY > j && mouseY < j + 28) {
				this.drawHoveringText(I18n.format("modetab." + ttab.name() + ".name"), mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		
		if (mouseButton == 0) {
			for (EnumModeTab ttab : EnumModeTab.values()) {
				if (mouseX > i + 28 * ttab.ordinal() && mouseX < i + 28 * ttab.ordinal() + 28 && mouseY > j && mouseY < j + 28) {
					this.setModeTab(ttab);
				}
			}
		}
	}
	
	private void drawForgroundTab() {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 28;
		this.drawTexturedModalRect(i + (28 * tab.ordinal()), j, 28 * tab.ordinal(), 32, 28, 32);
	}
	
	private void drawTabs(int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2 - 24;
		this.drawTexturedModalRect(i, j, 0, 2, 28 * EnumModeTab.values().length, 32);
	}
	
	public final void setBackground(ResourceLocation background) {
		this.normal_background = background;
		if (tab == EnumModeTab.NORMAL) {
			this.used_background = this.normal_background;
		}
	}
	
	public final EnumModeTab getTab() {
		return tab;
	}
	
	public final void setModeTab(EnumModeTab tab) {
		this.tab = tab;
		initTab(tab);
	}
	
	private void initTab(EnumModeTab tab) {
		if (tab == EnumModeTab.NORMAL) {
			this.used_background = normal_background;
		} else if (tab == EnumModeTab.ENERGY) {
			this.used_background = ENERGY;
		}
	}
	
	private void drawEnergyTab(float partialTicks, int mouseX, int mouseY) {
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
	}
	
}
