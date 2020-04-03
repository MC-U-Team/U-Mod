package info.u_team.u_mod.integration.jei;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.init.UModBlocks;
import mezz.jei.api.*;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class UModJeiPlugin implements IModPlugin {
	
	private final ResourceLocation id = new ResourceLocation(UMod.MODID, "jei");
	
	@Override
	public ResourceLocation getPluginUid() {
		return id;
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(UModBlocks.ELECTRIC_FURNACE, VanillaRecipeCategoryUid.FURNACE);
	}
	
}
