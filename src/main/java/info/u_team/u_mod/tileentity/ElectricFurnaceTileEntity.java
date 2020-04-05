package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.container.ElectricFurnaceContainer;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeData;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.*;
import net.minecraft.util.text.*;

public class ElectricFurnaceTileEntity extends BasicMachineTileEntity<FurnaceRecipe> {
	
	public ElectricFurnaceTileEntity() {
		super(UModTileEntityTypes.ELECTRIC_FURNACE, 20000, 100, 0, IRecipeType.SMELTING, 1, 6, 3, RecipeData.getBasicCooking(0, 5));
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.umod.electric_furnace");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new ElectricFurnaceContainer(id, playerInventory, this);
	}
	
}
