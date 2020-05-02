package info.u_team.u_mod.util.recipe;

import info.u_team.u_mod.block.basic.BasicMachineBlock;
import info.u_team.u_mod.tileentity.basic.BasicEnergyTileEntity;
import info.u_team.u_team_core.inventory.UItemStackHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityRecipeHandler<T extends IRecipe<IInventory>, X extends BasicEnergyTileEntity> extends RecipeHandler<T> {
	
	public TileEntityRecipeHandler(X tileEntity, IRecipeType<T> recipeType, int ingredientSize, LazyOptional<UItemStackHandler> ingredientSlotsOptional, LazyOptional<UItemStackHandler> outputSlotsOptional, LazyOptional<UItemStackHandler> upgradeSlotsOptional, RecipeData<T> recipeData) {
		super(recipeType, tileEntity.getInternalEnergyStorageOptional(), ingredientSize, ingredientSlotsOptional, outputSlotsOptional, upgradeSlotsOptional, recipeData, tileEntity::markDirty, newState -> {
			if (newState != tileEntity.getBlockState().get(BasicMachineBlock.WORKING)) {
				tileEntity.getWorld().setBlockState(tileEntity.getPos(), tileEntity.getBlockState().with(BasicMachineBlock.WORKING, newState), 3);
				tileEntity.updateContainingBlockInfo();
			}
		});
	}
	
}
