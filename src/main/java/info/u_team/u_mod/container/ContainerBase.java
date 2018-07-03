package info.u_team.u_mod.container;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import info.u_team.u_mod.container.slot.SlotPlayerInventory;
import info.u_team.u_mod.resource.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBase extends UContainer {
	
	public EntityPlayer player;
	public World world;
	public BlockPos pos;
	public IInventory tile;
	public final int[] fields;
	private EnumModeTab tab = EnumModeTab.NORMAL;
	public final EnumMap<EnumModeTab, List<Slot>> slots = new EnumMap<>(EnumModeTab.class);
	public final EnumMap<EnumModeTab, NonNullList<ItemStack>> items = new EnumMap<>(EnumModeTab.class);
	
	public ContainerBase(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.world = world;
		this.pos = pos;
		
		this.tile = (IInventory) world.getTileEntity(pos);
		this.fields = new int[this.tile.getFieldCount()];
		
		for(EnumModeTab mode : EnumModeTab.values()) {
			slots.put(mode, Lists.newArrayList());
			items.put(mode, NonNullList.create());
		}
		
		for (int height = 0; height < 4; height++) {
			for (int width = 0; width < 9; width++) {
				if (height == 3) {
					addSlot(EnumModeTab.NORMAL, new SlotPlayerInventory(player.inventory, width, width * 18 + 8, height * 18 + 4 + 84));
					continue;
				}
				addSlot(EnumModeTab.NORMAL, new SlotPlayerInventory(player.inventory, width + height * 9 + 9, width * 18 + 8, height * 18 + 84));
			}
		}
	}
	
	public final Slot addSlot(EnumModeTab tab, Slot slot) {
		slot.slotNumber = slots.get(tab).size();
		items.get(tab).add(ItemStack.EMPTY);
		slots.get(tab).add(slot);
		return slot;
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (fields.length > 0) {
			listener.sendAllWindowProperties(this, this.tile);
		}
	}
	
	public final EnumModeTab getTab() {
		return tab;
	}
	
	public final void setTab(EnumModeTab tab) {
		this.tab = tab;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tile.setField(id, data);
	}
	
	@Override
	public void putStackInSlot(int slotID, ItemStack stack) {
		if (this.tab.equals(EnumModeTab.NORMAL)) {
			super.putStackInSlot(slotID, stack);
		}
	}
	
	// -----------------------------------------------------------------------------
	// Mode override
	// -----------------------------------------------------------------------------
	
	
    /** The current drag mode (0 : evenly split, 1 : one item by slot, 2 : not used ?) */
    private int dragMode = -1;
    /** The current drag event (0 : start, 1 : add slot : 2 : end) */
    private int dragEvent;
    /** The list of slots where the itemstack holds will be distributed */
    private final Set<Slot> dragSlots = Sets.<Slot>newHashSet();
	
	/**
	 * Remove this method
	 */
	@Override
	protected final Slot addSlotToContainer(Slot slotIn) {
		throw new IllegalAccessError("addSlotToContainer not supported in ContainerBase.");
	}
	
	/**
	 * returns a list if itemStacks, for each slot.
	 */
	public NonNullList<ItemStack> getInventory() {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack> create();
		
		for (EnumModeTab ttab : EnumModeTab.values()) {
			for (int i = 0; i < this.slots.get(ttab).size(); ++i) {
				nonnulllist.add(((Slot) this.slots.get(ttab).get(i)).getStack());
			}
		}
		return nonnulllist;
	}
	
	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		for (int j = 0; j < fields.length; j++) {
			for (int i = 0; i < this.listeners.size(); ++i) {
				IContainerListener icontainerlistener = this.listeners.get(i);
				if (this.fields[j] != this.tile.getField(j)) {
					icontainerlistener.sendWindowProperty(this, j, this.tile.getField(j));
				}
			}
			this.fields[j] = this.tile.getField(0);
		}
		
		for (EnumModeTab tabs : EnumModeTab.values()) {
			for (int i = 0; i < this.slots.get(tabs).size(); ++i) {
				ItemStack itemstack = ((Slot) this.slots.get(tabs).get(i)).getStack();
				ItemStack itemstack1 = this.items.get(tabs).get(i);
				
				if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
					boolean clientStackChanged = !ItemStack.areItemStacksEqualUsingNBTShareTag(itemstack1, itemstack);
					itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
					this.items.get(tabs).set(i, itemstack1);
					
					if (clientStackChanged)
						for (int j = 0; j < this.listeners.size(); ++j) {
							((IContainerListener) this.listeners.get(j)).sendSlotContents(this, i, itemstack1);
						}
				}
			}
		}
		
	}
	
	@Nullable
	public Slot getSlotFromInventory(IInventory inv, int slotIn) {
		for (EnumModeTab mode : EnumModeTab.values()) {
			for (int i = 0; i < this.slots.get(mode).size(); ++i) {
				Slot slot = this.slots.get(mode).get(i);
				
				if (slot.isHere(inv, slotIn)) {
					return slot;
				}
			}
		}
		return null;
	}
	
	public Slot getSlot(int slotId) {
		return this.slots.get(tab).get(slotId);
	}
	
	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this
	 * moves the stack between the player inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = this.slots.get(tab).get(index);
		return slot != null ? slot.getStack() : ItemStack.EMPTY;
	}
	
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		ItemStack itemstack = ItemStack.EMPTY;
		InventoryPlayer inventoryplayer = player.inventory;
		
		if (clickTypeIn == ClickType.QUICK_CRAFT) {
			int j1 = this.dragEvent;
			this.dragEvent = getDragEvent(dragType);
			
			if ((j1 != 1 || this.dragEvent != 2) && j1 != this.dragEvent) {
				this.resetDrag();
			} else if (inventoryplayer.getItemStack().isEmpty()) {
				this.resetDrag();
			} else if (this.dragEvent == 0) {
				this.dragMode = extractDragMode(dragType);
				
				if (isValidDragMode(this.dragMode, player)) {
					this.dragEvent = 1;
					this.dragSlots.clear();
				} else {
					this.resetDrag();
				}
			} else if (this.dragEvent == 1) {
				Slot slot7 = this.slots.get(tab).get(slotId);
				ItemStack itemstack12 = inventoryplayer.getItemStack();
				
				if (slot7 != null && canAddItemToSlot(slot7, itemstack12, true) && slot7.isItemValid(itemstack12) && (this.dragMode == 2 || itemstack12.getCount() > this.dragSlots.size()) && this.canDragIntoSlot(slot7)) {
					this.dragSlots.add(slot7);
				}
			} else if (this.dragEvent == 2) {
				if (!this.dragSlots.isEmpty()) {
					ItemStack itemstack9 = inventoryplayer.getItemStack().copy();
					int k1 = inventoryplayer.getItemStack().getCount();
					
					for (Slot slot8 : this.dragSlots) {
						ItemStack itemstack13 = inventoryplayer.getItemStack();
						
						if (slot8 != null && canAddItemToSlot(slot8, itemstack13, true) && slot8.isItemValid(itemstack13) && (this.dragMode == 2 || itemstack13.getCount() >= this.dragSlots.size()) && this.canDragIntoSlot(slot8)) {
							ItemStack itemstack14 = itemstack9.copy();
							int j3 = slot8.getHasStack() ? slot8.getStack().getCount() : 0;
							computeStackSize(this.dragSlots, this.dragMode, itemstack14, j3);
							int k3 = Math.min(itemstack14.getMaxStackSize(), slot8.getItemStackLimit(itemstack14));
							
							if (itemstack14.getCount() > k3) {
								itemstack14.setCount(k3);
							}
							
							k1 -= itemstack14.getCount() - j3;
							slot8.putStack(itemstack14);
						}
					}
					
					itemstack9.setCount(k1);
					inventoryplayer.setItemStack(itemstack9);
				}
				
				this.resetDrag();
			} else {
				this.resetDrag();
			}
		} else if (this.dragEvent != 0) {
			this.resetDrag();
		} else if ((clickTypeIn == ClickType.PICKUP || clickTypeIn == ClickType.QUICK_MOVE) && (dragType == 0 || dragType == 1)) {
			if (slotId == -999) {
				if (!inventoryplayer.getItemStack().isEmpty()) {
					if (dragType == 0) {
						player.dropItem(inventoryplayer.getItemStack(), true);
						inventoryplayer.setItemStack(ItemStack.EMPTY);
					}
					
					if (dragType == 1) {
						player.dropItem(inventoryplayer.getItemStack().splitStack(1), true);
					}
				}
			} else if (clickTypeIn == ClickType.QUICK_MOVE) {
				if (slotId < 0) {
					return ItemStack.EMPTY;
				}
				
				Slot slot5 = this.slots.get(tab).get(slotId);
				
				if (slot5 == null || !slot5.canTakeStack(player)) {
					return ItemStack.EMPTY;
				}
				
				for (ItemStack itemstack7 = this.transferStackInSlot(player, slotId); !itemstack7.isEmpty() && ItemStack.areItemsEqual(slot5.getStack(), itemstack7); itemstack7 = this.transferStackInSlot(player, slotId)) {
					itemstack = itemstack7.copy();
				}
			} else {
				if (slotId < 0) {
					return ItemStack.EMPTY;
				}
				
				Slot slot6 = this.slots.get(tab).get(slotId);
				
				if (slot6 != null) {
					ItemStack itemstack8 = slot6.getStack();
					ItemStack itemstack11 = inventoryplayer.getItemStack();
					
					if (!itemstack8.isEmpty()) {
						itemstack = itemstack8.copy();
					}
					
					if (itemstack8.isEmpty()) {
						if (!itemstack11.isEmpty() && slot6.isItemValid(itemstack11)) {
							int i3 = dragType == 0 ? itemstack11.getCount() : 1;
							
							if (i3 > slot6.getItemStackLimit(itemstack11)) {
								i3 = slot6.getItemStackLimit(itemstack11);
							}
							
							slot6.putStack(itemstack11.splitStack(i3));
						}
					} else if (slot6.canTakeStack(player)) {
						if (itemstack11.isEmpty()) {
							if (itemstack8.isEmpty()) {
								slot6.putStack(ItemStack.EMPTY);
								inventoryplayer.setItemStack(ItemStack.EMPTY);
							} else {
								int l2 = dragType == 0 ? itemstack8.getCount() : (itemstack8.getCount() + 1) / 2;
								inventoryplayer.setItemStack(slot6.decrStackSize(l2));
								
								if (itemstack8.isEmpty()) {
									slot6.putStack(ItemStack.EMPTY);
								}
								
								slot6.onTake(player, inventoryplayer.getItemStack());
							}
						} else if (slot6.isItemValid(itemstack11)) {
							if (itemstack8.getItem() == itemstack11.getItem() && itemstack8.getMetadata() == itemstack11.getMetadata() && ItemStack.areItemStackTagsEqual(itemstack8, itemstack11)) {
								int k2 = dragType == 0 ? itemstack11.getCount() : 1;
								
								if (k2 > slot6.getItemStackLimit(itemstack11) - itemstack8.getCount()) {
									k2 = slot6.getItemStackLimit(itemstack11) - itemstack8.getCount();
								}
								
								if (k2 > itemstack11.getMaxStackSize() - itemstack8.getCount()) {
									k2 = itemstack11.getMaxStackSize() - itemstack8.getCount();
								}
								
								itemstack11.shrink(k2);
								itemstack8.grow(k2);
							} else if (itemstack11.getCount() <= slot6.getItemStackLimit(itemstack11)) {
								slot6.putStack(itemstack11);
								inventoryplayer.setItemStack(itemstack8);
							}
						} else if (itemstack8.getItem() == itemstack11.getItem() && itemstack11.getMaxStackSize() > 1 && (!itemstack8.getHasSubtypes() || itemstack8.getMetadata() == itemstack11.getMetadata()) && ItemStack.areItemStackTagsEqual(itemstack8, itemstack11) && !itemstack8.isEmpty()) {
							int j2 = itemstack8.getCount();
							
							if (j2 + itemstack11.getCount() <= itemstack11.getMaxStackSize()) {
								itemstack11.grow(j2);
								itemstack8 = slot6.decrStackSize(j2);
								
								if (itemstack8.isEmpty()) {
									slot6.putStack(ItemStack.EMPTY);
								}
								
								slot6.onTake(player, inventoryplayer.getItemStack());
							}
						}
					}
					
					slot6.onSlotChanged();
				}
			}
		} else if (clickTypeIn == ClickType.SWAP && dragType >= 0 && dragType < 9) {
			Slot slot4 = this.slots.get(tab).get(slotId);
			ItemStack itemstack6 = inventoryplayer.getStackInSlot(dragType);
			ItemStack itemstack10 = slot4.getStack();
			
			if (!itemstack6.isEmpty() || !itemstack10.isEmpty()) {
				if (itemstack6.isEmpty()) {
					if (slot4.canTakeStack(player)) {
						inventoryplayer.setInventorySlotContents(dragType, itemstack10);
						//Can't use that / wont use that
						//slot4.onSwapCraft(itemstack10.getCount());
						slot4.putStack(ItemStack.EMPTY);
						slot4.onTake(player, itemstack10);
					}
				} else if (itemstack10.isEmpty()) {
					if (slot4.isItemValid(itemstack6)) {
						int l1 = slot4.getItemStackLimit(itemstack6);
						
						if (itemstack6.getCount() > l1) {
							slot4.putStack(itemstack6.splitStack(l1));
						} else {
							slot4.putStack(itemstack6);
							inventoryplayer.setInventorySlotContents(dragType, ItemStack.EMPTY);
						}
					}
				} else if (slot4.canTakeStack(player) && slot4.isItemValid(itemstack6)) {
					int i2 = slot4.getItemStackLimit(itemstack6);
					
					if (itemstack6.getCount() > i2) {
						slot4.putStack(itemstack6.splitStack(i2));
						slot4.onTake(player, itemstack10);
						
						if (!inventoryplayer.addItemStackToInventory(itemstack10)) {
							player.dropItem(itemstack10, true);
						}
					} else {
						slot4.putStack(itemstack6);
						inventoryplayer.setInventorySlotContents(dragType, itemstack10);
						slot4.onTake(player, itemstack10);
					}
				}
			}
		} else if (clickTypeIn == ClickType.CLONE && player.capabilities.isCreativeMode && inventoryplayer.getItemStack().isEmpty() && slotId >= 0) {
			Slot slot3 = this.slots.get(tab).get(slotId);
			
			if (slot3 != null && slot3.getHasStack()) {
				ItemStack itemstack5 = slot3.getStack().copy();
				itemstack5.setCount(itemstack5.getMaxStackSize());
				inventoryplayer.setItemStack(itemstack5);
			}
		} else if (clickTypeIn == ClickType.THROW && inventoryplayer.getItemStack().isEmpty() && slotId >= 0) {
			Slot slot2 = this.slots.get(tab).get(slotId);
			
			if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player)) {
				ItemStack itemstack4 = slot2.decrStackSize(dragType == 0 ? 1 : slot2.getStack().getCount());
				slot2.onTake(player, itemstack4);
				player.dropItem(itemstack4, true);
			}
		} else if (clickTypeIn == ClickType.PICKUP_ALL && slotId >= 0) {
			Slot slot = this.slots.get(tab).get(slotId);
			ItemStack itemstack1 = inventoryplayer.getItemStack();
			
			if (!itemstack1.isEmpty() && (slot == null || !slot.getHasStack() || !slot.canTakeStack(player))) {
				int i = dragType == 0 ? 0 : this.slots.get(tab).size() - 1;
				int j = dragType == 0 ? 1 : -1;
				
				for (int k = 0; k < 2; ++k) {
					for (int l = i; l >= 0 && l < this.slots.get(tab).size() && itemstack1.getCount() < itemstack1.getMaxStackSize(); l += j) {
						Slot slot1 = this.slots.get(tab).get(l);
						
						if (slot1.getHasStack() && canAddItemToSlot(slot1, itemstack1, true) && slot1.canTakeStack(player) && this.canMergeSlot(itemstack1, slot1)) {
							ItemStack itemstack2 = slot1.getStack();
							
							if (k != 0 || itemstack2.getCount() != itemstack2.getMaxStackSize()) {
								int i1 = Math.min(itemstack1.getMaxStackSize() - itemstack1.getCount(), itemstack2.getCount());
								ItemStack itemstack3 = slot1.decrStackSize(i1);
								itemstack1.grow(i1);
								
								if (itemstack3.isEmpty()) {
									slot1.putStack(ItemStack.EMPTY);
								}
								
								slot1.onTake(player, itemstack3);
							}
						}
					}
				}
			}
			
			this.detectAndSendChanges();
		}
		
		return itemstack;
	}
	
	/**
	 * Merges provided ItemStack with the first avaliable one in the
	 * container/player inventor between minIndex (included) and maxIndex
	 * (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the
	 * Container implementation do not check if the item is valid for the slot
	 */
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
				
				Slot slot = this.slots.get(tab).get(i);
				ItemStack itemstack = slot.getStack();
				
				if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
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
				
				Slot slot1 = this.slots.get(tab).get(i);
				ItemStack itemstack1 = slot1.getStack();
				
				if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
					if (stack.getCount() > slot1.getSlotStackLimit()) {
						slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
					} else {
						slot1.putStack(stack.splitStack(stack.getCount()));
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
