package info.u_team.u_mod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreIngredient;

public class RecipeShapelessOre extends ShapelessRecipes {
	
	public RecipeShapelessOre(String group, String input, int inputsize, ItemStack output) {
		super(group, output, NonNullList.withSize(inputsize, new OreIngredient(input)));
	}
	
}
