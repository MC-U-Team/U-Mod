package info.u_team.u_mod.util.recipe;

import info.u_team.u_mod.tileentity.basic.BasicEnergyTileEntity;
import info.u_team.u_mod.util.inventory.ObservableTileEntityStackHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;

public class TileEntityRecipeHandler<T extends IRecipe<IInventory>, X extends BasicEnergyTileEntity> extends RecipeHandler<T> {
	
	public TileEntityRecipeHandler(X tileEntity, IRecipeType<T> recipeType, int ingredientSize, int outputSize, int upgradeSize, RecipeData<T> recipeData) {
		super(recipeType, tileEntity.getInternalEnergyStorage(), new ObservableTileEntityStackHandler(ingredientSize, tileEntity), new ObservableTileEntityStackHandler(outputSize, tileEntity), new ObservableTileEntityStackHandler(upgradeSize, tileEntity), recipeData, tileEntity::markDirty);
	}
	
}
