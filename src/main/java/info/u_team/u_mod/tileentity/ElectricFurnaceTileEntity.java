package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.container.ElectricFurnaceContainer;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_mod.util.recipe.*;
import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;

public class ElectricFurnaceTileEntity extends BasicEnergyTileEntity implements INamedContainerProvider, ITickableTileEntity, IInitSyncedTileEntity {
	
	private final RecipeHandler<FurnaceRecipe> recipeHandler;
	
	public ElectricFurnaceTileEntity() {
		super(UModTileEntityTypes.ENERGY_FURNANCE, 20000, 100, 0, 10000);
		recipeHandler = new TileEntityRecipeHandler<>(this, IRecipeType.SMELTING, 1, 1, RecipeData.getBasicCooking(0, 5));
	}
	
	// Tick
	@Override
	protected void tickServer() {
		recipeHandler.update(world);
	}
	
	// NBT
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		super.writeNBT(compound);
		compound.put("recipe_data", recipeHandler.serializeNBT());
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
		recipeHandler.deserializeNBT(compound.getCompound("recipe_data"));
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Electric Furnace");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new ElectricFurnaceContainer(id, playerInventory, this);
	}
	
	// Invalidate lazy optional
	
	@Override
	public void remove() {
		super.remove();
		recipeHandler.invalidate();
	}
	
	// Inital send when container is opened
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		super.sendInitialDataBuffer(buffer);
		recipeHandler.sendInitialDataBuffer(buffer);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		super.handleInitialDataBuffer(buffer);
		recipeHandler.handleInitialDataBuffer(buffer);
	}
	
	// Getter
	public RecipeHandler<FurnaceRecipe> getRecipeHandler() {
		return recipeHandler;
	}
}
