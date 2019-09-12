package info.u_team.u_mod.gui;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.CrateContainer;
import info.u_team.u_team_core.gui.UContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class CrateScreen extends UContainerScreen<CrateContainer> {
	
	public CrateScreen(CrateContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, new ResourceLocation(UMod.MODID, "textures/gui/crate/" + container.getTileEntity().getCrate().getName() + ".png"));
	}
	
}
