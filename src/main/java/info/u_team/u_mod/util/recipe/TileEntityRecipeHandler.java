package info.u_team.u_mod.util.recipe;

import info.u_team.u_mod.tileentity.basic.BasicEnergyTileEntity;
import info.u_team.u_mod.util.inventory.BasicTileEntityStackHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;

public class TileEntityRecipeHandler<T extends IRecipe<IInventory>, X extends BasicEnergyTileEntity> extends RecipeHandler<T> {
	
	public TileEntityRecipeHandler(X tileEntity, IRecipeType<T> recipeType, int ingredientSize, int outputSize, int upgradeSize, RecipeData<T> recipeData) {
		super(recipeType, tileEntity.getInternalEnergyStorage(), new BasicTileEntityStackHandler(ingredientSize, tileEntity), new BasicTileEntityStackHandler(outputSize, tileEntity), new BasicTileEntityStackHandler(upgradeSize, tileEntity), recipeData, tileEntity::markDirty);
	}
	
}
