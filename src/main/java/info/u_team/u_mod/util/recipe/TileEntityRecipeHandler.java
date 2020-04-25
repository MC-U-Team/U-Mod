package info.u_team.u_mod.util.recipe;

import info.u_team.u_mod.tileentity.basic.BasicEnergyTileEntity;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;

public class TileEntityRecipeHandler<T extends IRecipe<IInventory>, X extends BasicEnergyTileEntity> extends RecipeHandler<T> {
	
	public TileEntityRecipeHandler(X tileEntity, IRecipeType<T> recipeType, int ingredientSize, int outputSize, int upgradeSize, RecipeData<T> recipeData) {
		super(recipeType, tileEntity.getInternalEnergyStorage(), new TileEntityUItemStackHandler(ingredientSize, tileEntity), new TileEntityUItemStackHandler(outputSize, tileEntity), new TileEntityUItemStackHandler(upgradeSize, tileEntity), recipeData, tileEntity::markDirty);
	}
	
}
