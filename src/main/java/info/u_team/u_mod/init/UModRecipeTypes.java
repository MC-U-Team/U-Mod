package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.recipe.*;
import net.minecraft.item.crafting.*;

public class UModRecipeTypes {
	
	public static final IRecipeType<OneIngredientMachineRecipe> CRUSHER = register("crusher");
	
	private static <T extends IRecipe<?>> IRecipeType<T> register(String name) {
		return IRecipeType.register(UMod.MODID + ":" + name);
	}
	
}
