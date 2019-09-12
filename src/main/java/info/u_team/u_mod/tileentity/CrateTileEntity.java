package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.api.type.ICrate;
import info.u_team.u_mod.container.CrateContainer;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.*;

public class CrateTileEntity extends UTileEntity implements INamedContainerProvider {
	
	private final ICrate crate;
	
	private final LazyOptional<ItemStackHandler> slots;
	
	public CrateTileEntity(TileEntityType<?> type, ICrate crate) {
		super(type);
		this.crate = crate;
		slots = LazyOptional.of(() -> new ItemStackHandler(crate.getInventorySize()));
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		slots.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		slots.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slots.cast();
		}
		return super.getCapability(capability, side);
	}
	
	@Override
	public void remove() {
		super.remove();
		slots.invalidate();
	}
	
	public ICrate getCrate() {
		return crate;
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new CrateContainer(id, playerInventory, this);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(crate.getName());
	}
}
