package info.u_team.u_mod.screen;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.CrusherContainer;
import info.u_team.u_mod.gui.ProgressWidget;
import info.u_team.u_mod.screen.basic.BasicMachineScreen;
import info.u_team.u_mod.util.recipe.RecipeHandler;
import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrusherScreen extends BasicMachineScreen<CrusherContainer> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(UMod.MODID, "textures/gui/machine/crusher.png");
	
	public CrusherScreen(CrusherContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, TEXTURE);
	}
	
	@Override
	protected void init() {
		super.init();
		final RecipeHandler<?> handler = container.getTileEntity().getRecipeHandler();
		addButton(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, handler.getEnergyOptional().cast()));
		addButton(ProgressWidget.createBasicArrow(guiLeft + 67, guiTop + 39, 42, 17, handler::getPercent));
	}
}
