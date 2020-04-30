package info.u_team.u_mod.screen;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.ElectricFurnaceContainer;
import info.u_team.u_mod.gui.ProgressWidget;
import info.u_team.u_mod.screen.basic.BasicMachineScreen;
import info.u_team.u_mod.tileentity.ElectricFurnaceTileEntity;
import info.u_team.u_team_core.gui.elements.EnergyStorageWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ElectricFurnaceScreen extends BasicMachineScreen<ElectricFurnaceContainer> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(UMod.MODID, "textures/gui/machine/electric_furnace.png");
	
	public ElectricFurnaceScreen(ElectricFurnaceContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, TEXTURE);
	}
	
	@Override
	protected void init() {
		super.init();
		final ElectricFurnaceTileEntity handler = container.getTileEntity();
		addButton(new EnergyStorageWidget(guiLeft + 9, guiTop + 20, 54, handler::getInternalEnergyStorage));
		addButton(ProgressWidget.createLongBasicArrow(guiLeft + 67, guiTop + 50, handler.getRecipeHandler()::getPercent));
	}
}
