package info.u_team.u_mod.tilentity.generation;

import info.u_team.u_mod.block.generation.BlockSolarPanel;
import info.u_team.u_mod.block.generation.BlockSolarPanel.EnumType;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntitySolarPanel extends TileEntityGeneration {
	
	private EnumType type = null;
	
	protected boolean working = false;
	
	private int cooldown = 0;
	
	public TileEntitySolarPanel() {
		super(0, "solarpanel");
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (type == null) {
			type = world.getBlockState(pos).getValue(BlockSolarPanel.TYPE);
			energy.setCapacity(type.getEnergy() * 100);
			energy.setTransfer(type.getEnergy() * 4);
		}
		if (working) {
			energy.receiveEnergy(type.getEnergy(), false);
		}
		cooldown++;
		if (cooldown >= 20) {
			cooldown = 0;
			checkWorking();
		}
	}
	
	private void checkWorking() {
		working = world.isDaytime();
		working = !world.isRaining();
		working = world.canBlockSeeSky(pos);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}
	
}
