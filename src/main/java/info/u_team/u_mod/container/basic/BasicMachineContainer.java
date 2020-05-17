package info.u_team.u_mod.container.basic;

import info.u_team.to_uteam_core.FluidTileEntityContainer;
import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;

public class BasicMachineContainer<T extends BasicMachineTileEntity<?>> extends FluidTileEntityContainer<T> {
	
	protected int playerInventoryStart = 10; // set standard to 10
	
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
		addServerToClientTracker(tileEntity.getRecipeHandler().getPercentTracker());
		addServerToClientTracker(tileEntity.getInternalEnergyStorage().createSyncHandler());
	}
	
	protected void appendOutputInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, BasicOutputItemHandlerSlot::new, inventoryHeight, inventoryWidth, x, y);
	}
	
	protected void appendUpgradeInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, BasicUpgradeItemHandlerSlot::new, inventoryHeight, inventoryWidth, x, y);
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
				if (mergeItemStack(stack, 0, playerInventoryStart, false)) {
					return ItemStack.EMPTY;
				}
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
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		boolean flag = false;
		int i = startIndex;
		if (reverseDirection) {
			i = endIndex - 1;
		}
		
		if (stack.isStackable()) {
			while (!stack.isEmpty()) {
				if (reverseDirection) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}
				
				final Slot slot = this.inventorySlots.get(i);
				final ItemStack itemstack = slot.getStack();
				if (!itemstack.isEmpty() && areItemsAndTagsEqual(stack, itemstack) && slot.isItemValid(stack)) {
					int j = itemstack.getCount() + stack.getCount();
					int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
					if (j <= maxSize) {
						stack.setCount(0);
						itemstack.setCount(j);
						slot.onSlotChanged();
						flag = true;
					} else if (itemstack.getCount() < maxSize) {
						stack.shrink(maxSize - itemstack.getCount());
						itemstack.setCount(maxSize);
						slot.onSlotChanged();
						flag = true;
					}
				}
				
				if (reverseDirection) {
					--i;
				} else {
					++i;
				}
			}
		}
		
		if (!stack.isEmpty()) {
			if (reverseDirection) {
				i = endIndex - 1;
			} else {
				i = startIndex;
			}
			
			while (true) {
				if (reverseDirection) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}
				
				final Slot slot1 = this.inventorySlots.get(i);
				final ItemStack itemstack1 = slot1.getStack();
				if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
					if (stack.getCount() > slot1.getSlotStackLimit()) {
						slot1.putStack(stack.split(slot1.getSlotStackLimit()));
					} else {
						slot1.putStack(stack.split(stack.getCount()));
					}
					
					slot1.onSlotChanged();
					flag = true;
					break;
				}
				
				if (reverseDirection) {
					--i;
				} else {
					++i;
				}
			}
		}
		
		return flag;
	}
}
