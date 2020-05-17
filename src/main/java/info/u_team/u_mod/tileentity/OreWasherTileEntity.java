package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.container.OreWasherContainer;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeData;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.*;

public class OreWasherTileEntity extends BasicMachineTileEntity<OneIngredientMachineRecipe> {
	
	public OreWasherTileEntity() {
		super(UModTileEntityTypes.ORE_WASHER, 20000, 100, 0, UModRecipeTypes.CRUSHER, 1, 6, 3, RecipeData.getBasicMachine());
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.umod.crusher");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new OreWasherContainer(id, playerInventory, this);
	}
	
}
