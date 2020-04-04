package info.u_team.u_mod.container;

import info.u_team.u_mod.container.basic.BasicMachineContainer;
import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.ElectricFurnaceTileEntity;
import info.u_team.u_mod.util.recipe.RecipeHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.network.PacketBuffer;

public class ElectricFurnaceContainer extends BasicMachineContainer<ElectricFurnaceTileEntity> {
	
	// Client
	public ElectricFurnaceContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(UModContainerTypes.ELECTRIC_FURNACE, id, playerInventory, buffer);
	}
	
	// Server
	public ElectricFurnaceContainer(int id, PlayerInventory playerInventory, ElectricFurnaceTileEntity tileEntity) {
		super(UModContainerTypes.ELECTRIC_FURNACE, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		super.init(server);
		final RecipeHandler<FurnaceRecipe> recipeHandler = tileEntity.getRecipeHandler();
		appendInventory(recipeHandler.getIngredientSlots(), 1, 1, 44, 50);
		appendOutputInventory(recipeHandler.getOutputSlots(), 1, 1, 116, 41);
		appendPlayerInventory(playerInventory, 8, 92);
	}
	
}
