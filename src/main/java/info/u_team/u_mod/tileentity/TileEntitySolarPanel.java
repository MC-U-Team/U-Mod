package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.api.machines.ISolarPanel;
import info.u_team.u_mod.block.BlockSolarPanel;
import info.u_team.u_mod.energy.EnergyProvider;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_team_core.tileentity.UTileEntityContainer;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class TileEntitySolarPanel extends UTileEntityContainer implements ITickable {
	
	private ISolarPanel solarpanel;
	private EnergyProvider energy;
	
	private boolean working;
	private int cooldown;
	
	public TileEntitySolarPanel() {
		super(UModTileEntityTypes.solarpanel);
		energy = new EnergyProvider();
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}
	
	@Override
	public ResourceLocation getGui() {
		return null;
	}
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		energy.setStorage(compound.getInt("storage"));
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInt("storage", energy.getStorage());
	}
	
	@Override
	public void onLoad() {
		BlockSolarPanel block = (BlockSolarPanel) getBlockState().getBlock();
		solarpanel = block.getSolarpanel();
		energy.setCapacity(solarpanel.getCapacity());
		energy.setTransfer(solarpanel.getTransfer());
		energy.setGeneration(solarpanel.getGeneration());
	}
	
	@Override
	public void tick() {
		if (world.isRemote) {
			return;
		}
		if (solarpanel == null) {
			System.out.println("ERRROR THIS SHOULD NEVER HAPPEN..............................");
			return;
		}
		if (working) {
			energy.tick();
		}
		if (cooldown >= 20) {
			cooldown = 0;
			working = world.isDaytime();
			working = !world.isRaining();
			working = world.canBlockSeeSky(pos);
		}
		cooldown++;
	}
	
}
