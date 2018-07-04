package info.u_team.u_mod.container;

import info.u_team.u_mod.gui.EnumModeTab;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class ContainerBase extends UContainer {
	
	public EntityPlayer player;
	public World world;
	public BlockPos pos;
	public IInventory tile;
	public final int[] fields;
	private EnumModeTab tab = EnumModeTab.NORMAL;
	
	public ContainerBase(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.world = world;
		this.pos = pos;
		
		this.tile = (IInventory) world.getTileEntity(pos);
		this.fields = new int[this.tile.getFieldCount()];
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tile);
	}
	
	public final EnumModeTab getTab() {
		return tab;
	}
	
	public final void setTab(EnumModeTab tab) {
		this.tab = tab;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int j = 0; j < fields.length; j++) {
			for (int i = 0; i < this.listeners.size(); ++i) {
				IContainerListener icontainerlistener = this.listeners.get(i);
				if (this.fields[j] != this.tile.getField(j)) {
					icontainerlistener.sendWindowProperty(this, j, this.tile.getField(j));
				}
			}
			this.fields[j] = this.tile.getField(0);
		}
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
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (this.tab.equals(EnumModeTab.NORMAL)) {
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		}
		return ItemStack.EMPTY;
	}
	
}
