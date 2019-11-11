package info.u_team.u_mod.screen;

import info.u_team.u_mod.container.ElectricFurnaceContainer;
import info.u_team.u_team_core.gui.UContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ElectricFurnaceScreen extends UContainerScreen<ElectricFurnaceContainer> {
	
	public ElectricFurnaceScreen(ElectricFurnaceContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation("textures/gui/container/furnace.png"));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
	}
	
	@Override
	public void tick() {
		super.tick();
//	System.out.println(container.getTileEntity().getRecipeHandler().getPercent());
	}
	
}
