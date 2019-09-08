package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.block.CrateBlock;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_mod.type.Crate;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.*;

public class CrateTileEntity extends UTileEntity implements INamedContainerProvider {
	
	private final LazyOptional<Crate> crate = LazyOptional.of(() -> {
		final Block block = getBlockState().getBlock();
		return block instanceof CrateBlock ? ((CrateBlock) block).getCrate() : null;
	});
	
	private final LazyOptional<ItemStackHandler> slots = LazyOptional.of(() -> new ItemStackHandler(crate.orElse(null).getInventorySize()));
	
	public CrateTileEntity() {
		super(UModTileEntityTypes.CRATE);
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
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return null;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return null;
	}
}
