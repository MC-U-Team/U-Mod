package info.u_team.u_mod.container.basic;

import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeHandler;
import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;

public class BasicMachineContainer<T extends BasicMachineTileEntity<?>> extends UTileEntityContainer<T> {
	
	// Client
	public BasicMachineContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(type, id, playerInventory, buffer);
	}
	
	// Server
	public BasicMachineContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, T tileEntity) {
		super(type, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		final RecipeHandler<?> recipeHandler = tileEntity.getRecipeHandler();
		addServerToClientTracker(recipeHandler.getPercentTracker());
		recipeHandler.getEnergy().ifPresent(storage -> addServerToClientTracker(storage.createSyncHandler()));
	}
	
	protected void addOutputInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, BasicOutputItemHandlerSlot::new, inventoryHeight, inventoryWidth, x, y);
	}
	
}
