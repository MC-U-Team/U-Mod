package info.u_team.u_mod.tilentity.energy;

import info.u_team.u_mod.api.IClientProgress;
import info.u_team.u_mod.container.energy.ContainerCoalGenerator;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityCoalGenerator extends TileEntityGeneration implements IClientProgress {
	
	protected int max_progress = 100;
	protected int progress = max_progress;
	
	@SideOnly(Side.CLIENT)
	public int progress_client;
	
	public TileEntityCoalGenerator() {
		super(1, "coalgenerator");
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		ItemStack stack = getStackInSlot(0);
		if (isItemValidForSlot(0, stack) && stack.getCount() > 0) {
			progress--;
			if (progress <= 0) {
				stack.shrink(1);
				ienergy.receiveEnergy(1000, false);
				progress = max_progress;
				super.markDirty();
			}
		} else {
			progress = max_progress;
		}
	}
	
	@Override
	public int getField(int id) {
		if (id == 2) {
			return 100 - (int) (((float) progress / (float) max_progress) * 100);
		}
		return super.getField(id);
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 2) {
			progress_client = value;
		} else {
			super.setField(id, value);
		}
	}
	
	@Override
	public int getFieldCount() {
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplProgress() {
		return progress_client;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] { 0 };
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getItem().equals(Items.COAL) || stack.getItem().equals(Item.getItemFromBlock(Blocks.COAL_BLOCK));
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return new ContainerCoalGenerator(player, world, pos);
	}
	
}
