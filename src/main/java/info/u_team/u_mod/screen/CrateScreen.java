package info.u_team.u_mod.screen;

import info.u_team.u_mod.block.CrateContainer;
import info.u_team.u_team_core.gui.UContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class CrateScreen extends UContainerScreen<CrateContainer> {
	
	public CrateScreen(CrateContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, null);
	}
	
}
