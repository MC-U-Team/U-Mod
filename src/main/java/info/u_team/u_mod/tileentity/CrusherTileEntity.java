package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.container.CrusherContainer;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeData;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.*;

public class CrusherTileEntity extends BasicMachineTileEntity<OneIngredientMachineRecipe> {
	
	public CrusherTileEntity() {
		super(UModTileEntityTypes.CRUSHER, 20000, 100, 0, UModRecipeTypes.CRUSHER, 1, 6, RecipeData.getBasicMachine());
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.umod.crusher");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new CrusherContainer(id, playerInventory, this);
	}
	
}
