package info.u_team.u_mod.gui.energy;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.*;
import info.u_team.u_mod.container.ContainerBase;
import info.u_team.u_mod.gui.UGuiContainer;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class GuiLavaGenerator extends UGuiContainer {
	
	public GuiLavaGenerator(UContainer container) {
		super((ContainerBase) container);
		setBackground(new ResourceLocation(UConstants.MODID, "textures/gui/lava_generator.png"));
	}
	
	@Override
	protected void drawInBackground(EnumModeTab tab, int mouseX, int mouseY, int x_offset, int y_offset) {
		if (tab == EnumModeTab.NORMAL) {
			IClientProgress iclient = (IClientProgress) getContainer().tile;
			// drawTexturedModalRect(x_offset + 81 + 14, y_offset + 55 + 14, 176 + 14, 0 +
			// 14, -14, -1 * Math.round(14 * iclient.getImplProgress() / 100));
			
			IClientFluidTank iclientfluidtank = (IClientFluidTank) getContainer().tile;
			
			Fluid fluid = iclientfluidtank.getImplFluid();
			
			if (fluid == null || iclientfluidtank.getImplFluidTank() == 0) {
				return;
			}
			
			TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
			mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			// int fluidHeight = iclientfluidtank.getImplFluidTank();
			drawTexturedModalRect5(x_offset + 62, y_offset + 14, fluidTexture, fluidTexture.getIconWidth(), fluidTexture.getIconWidth());
			
			// TODO @MRTROBLE MACHT DAS SOFORT!!!!!!
		}
	}
	
	public void drawTexturedModalRect5(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
		float test = 1F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + heightIn), (double) this.zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMinV() + 16 * test).endVertex();
		bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + heightIn), (double) this.zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMinV() + 16 * test).endVertex();
		bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + 0), (double) this.zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMinV()).endVertex();
		bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + 0), (double) this.zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMinV()).endVertex();
		tessellator.draw();
	}
}
