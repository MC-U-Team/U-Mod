package info.u_team.u_mod.container.basic;

import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeHandler;
import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;

public class BasicMachineContainer<T extends BasicMachineTileEntity<?>> extends UTileEntityContainer<T> {
	
	protected int playerInventoryStart = 2; // set standard to 2
	
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
		addServerToClientTracker(recipeHandler.getEnergy().createSyncHandler());
	}
	
	protected void appendOutputInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, BasicOutputItemHandlerSlot::new, inventoryHeight, inventoryWidth, x, y);
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		System.out.println(slotId);
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack remainingStack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			final ItemStack stack = slot.getStack();
			remainingStack = stack.copy();
			
			if (index < playerInventoryStart) {
				if (!mergeItemStack(stack, playerInventoryStart, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (index >= 27 + playerInventoryStart) {
					if (!mergeItemStack(stack, playerInventoryStart, 27 + playerInventoryStart, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					if (!mergeItemStack(stack, playerInventoryStart + 27, inventorySlots.size(), false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			
			if (stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		
		return remainingStack;
	}
	
}
