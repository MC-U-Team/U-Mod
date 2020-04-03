package info.u_team.u_mod.integration.jei;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.init.UModBlocks;
import info.u_team.u_mod.integration.jei.category.CrusherRecipeCategoryJei;
import mezz.jei.api.*;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class UModJeiPlugin implements IModPlugin {
	
	private final ResourceLocation id = new ResourceLocation(UMod.MODID, "jei");
	
	@Override
	public ResourceLocation getPluginUid() {
		return id;
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		final IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new CrusherRecipeCategoryJei(helper));
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(UModBlocks.ELECTRIC_FURNACE), VanillaRecipeCategoryUid.FURNACE);
	}
	
}
