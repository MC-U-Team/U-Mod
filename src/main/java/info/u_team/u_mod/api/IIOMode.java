package info.u_team.u_mod.api;

import java.util.EnumMap;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public interface IIOMode {
	
	void setSlotForFacing(EnumFacing face, int[] slots, boolean input);
	
	int[] getSlots(EnumFacing face);
	
	boolean canInsertItem(EnumFacing face, int index, ItemStack stack);
	
	boolean canExtractItem(EnumFacing face, int index, ItemStack stack);
	
	boolean isInput(EnumFacing face);
	
	IItemHandler get(EnumFacing face);
	
	class IOModeHandler implements IIOMode {
		
		private EnumMap<EnumFacing, IItemHandler> map = new EnumMap<>(EnumFacing.class);
		private EnumMap<EnumFacing, int[]> map_slots = new EnumMap<>(EnumFacing.class);
		private EnumMap<EnumFacing, Boolean> map_input = new EnumMap<>(EnumFacing.class);
		private ISidedInventory inv;
		
		public IOModeHandler(ISidedInventory inv) {
			this.inv = inv;
			for (EnumFacing face : EnumFacing.VALUES) {
				map.put(face, new SidedInvWrapper(inv, face));
				map_slots.put(face, new int[0]);
				map_input.put(face, false);
			}
		}
		
		public void setSlotForFacing(EnumFacing face, int[] slots, boolean input) {
			map_slots.put(face, slots);
			map_input.put(face, input);
		}
		
		public IItemHandler get(EnumFacing face) {
			return map.get(face);
		}
		
		@Override
		public int[] getSlots(EnumFacing face) {
			return map_slots.get(face);
		}
		
		@Override
		public boolean canInsertItem(EnumFacing face, int index, ItemStack stack) {
			if (!this.isInput(face))
				return false;
			int[] arr = map_slots.get(face);
			for (int i : arr) {
				if (i == index) {
					return this.inv.isItemValidForSlot(index, stack);
				}
			}
			return false;
		}
		
		@Override
		public boolean canExtractItem(EnumFacing face, int index, ItemStack stack) {
			if (this.isInput(face))
				return false;
			int[] arr = map_slots.get(face);
			for (int i : arr) {
				if (i == index) {
					return this.inv.isItemValidForSlot(index, stack);
				}
			}
			return false;
		}
		
		@Override
		public boolean isInput(EnumFacing face) {
			return map_input.get(face);
		}
		
	}
}
