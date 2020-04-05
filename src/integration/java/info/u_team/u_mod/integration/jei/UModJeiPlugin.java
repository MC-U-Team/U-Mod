package info.u_team.u_mod.integration.jei;

import java.util.Collection;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.integration.jei.category.CrusherRecipeCategoryJei;
import mezz.jei.api.*;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
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
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(getRecipes(UModRecipeTypes.CRUSHER), CrusherRecipeCategoryJei.ID);
	}
	
	private static <C extends IInventory, T extends IRecipe<C>> Collection<IRecipe<C>> getRecipes(IRecipeType<T> type) {
		return Minecraft.getInstance().world.getRecipeManager().getRecipes(type).values();
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(UModBlocks.ELECTRIC_FURNACE), VanillaRecipeCategoryUid.FURNACE);
	}
	
}
