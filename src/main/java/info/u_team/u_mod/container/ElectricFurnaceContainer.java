package info.u_team.u_mod.container;

import info.u_team.u_mod.init.UModContainerTypes;
import info.u_team.u_mod.tileentity.ElectricFurnaceTileEntity;
import info.u_team.u_mod.util.recipe.RecipeHandler;
import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.network.PacketBuffer;

public class ElectricFurnaceContainer extends UTileEntityContainer<ElectricFurnaceTileEntity> {
	
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
		final RecipeHandler<FurnaceRecipe> recipeHandler = tileEntity.getRecipeHandler();
		recipeHandler.getIngredient().ifPresent(handler -> appendInventory(handler, 1, 1, 56, 17));
		recipeHandler.getOutput().ifPresent(handler -> appendInventory(handler, 1, 1, 116, 35));
		appendPlayerInventory(playerInventory, 8, 84);
		addServerToClientTracker(recipeHandler.getPercentTracker());
		recipeHandler.getEnergy().ifPresent(storage -> addServerToClientTracker(storage.createSyncHandler()));
	}
	
}
