package info.u_team.u_mod.container;

import info.u_team.u_mod.container.slot.*;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class ContainerPulverizer extends UContainer {
	
	public EntityPlayer player;
	public World world;
	public BlockPos pos;
	public IInventory tile;
	public int timeleft;
	
	public ContainerPulverizer(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.world = world;
		this.pos = pos;
		
		tile = (IInventory) world.getTileEntity(pos);
		
		addSlotToContainer(new SlotOreInput(tile, 0, 30, 23));
		addSlotToContainer(new SlotOutput(tile, 1, 98, 54));
		addSlotToContainer(new SlotOutput(tile, 2, 126, 54));
		addSlotToContainer(new SlotOutput(tile, 3, 116, 24));
		
		appendPlayerInventory(player.inventory, 8, 84);
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tile);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);
			if (this.timeleft != this.tile.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
			}
		}
		
		this.timeleft = this.tile.getField(0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tile.setField(id, data);
	}
	
	// TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
}
