package info.u_team.to_uteam_core;

import info.u_team.u_team_core.gui.UContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

public class FluidContainerScreen<T extends FluidTileEntityContainer<?>> extends UContainerScreen<T> {
	
	public FluidContainerScreen(T container, PlayerInventory playerInventory, ITextComponent title, ResourceLocation background) {
		super(container, playerInventory, title, background);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		for (int index = 0; index < container.getFluidSlots().size(); index++) {
			drawFluidSlot(container.getFluidSlots().get(index));
		}
	}
	
	private void drawFluidSlot(FluidSlot slot) {
		final int x = slot.getX();
		final int y = slot.getY();
		final FluidStack stack = slot.getStack();
		// TODO
	}
	
}
