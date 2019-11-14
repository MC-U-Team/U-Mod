package info.u_team.u_mod.screen;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.ElectricFurnaceContainer;
import info.u_team.u_mod.gui.ProgressWidget;
import info.u_team.u_mod.util.recipe.RecipeHandler;
import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ElectricFurnaceScreen extends UContainerScreen<ElectricFurnaceContainer> {
	
	public ElectricFurnaceScreen(ElectricFurnaceContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation(UMod.MODID, "textures/gui/machine/electric_furnace.png"));
		xSize = 176;
		ySize = 174;
	}
	
	@Override
	protected void init() {
		super.init();
		final RecipeHandler<?> handler = container.getTileEntity().getRecipeHandler();
		addButton(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, handler.getEnergy().cast()));
		addButton(new ProgressWidget(guiLeft + 67, guiTop + 39, 42, 17, new ResourceLocation(UMod.MODID, "textures/gui/progress/basic_arrow.png"), handler::getPercent));
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		buttons.forEach(button -> button.renderToolTip(mouseX, mouseY));
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(title.getFormattedText(), 8, 6, 0x404040);
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8, ySize - 94, 0x404040);
	}
	
}
