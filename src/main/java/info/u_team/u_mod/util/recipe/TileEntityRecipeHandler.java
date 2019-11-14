package info.u_team.u_mod.util.recipe;

import info.u_team.u_mod.tileentity.basic.BasicEnergyTileEntity;
import info.u_team.u_mod.util.BasicTileEntityStackHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityRecipeHandler<T extends IRecipe<IInventory>, X extends BasicEnergyTileEntity> extends RecipeHandler<T> {
	
	public TileEntityRecipeHandler(X tileEntity, IRecipeType<T> recipeType, int ingredientSize, int outputSize, RecipeData<T> recipeData) {
		super(recipeType, tileEntity.getInternalStorage(), LazyOptional.of(() -> new BasicTileEntityStackHandler(ingredientSize, tileEntity)), LazyOptional.of(() -> new BasicTileEntityStackHandler(outputSize, tileEntity)), recipeData);
	}
	
}
