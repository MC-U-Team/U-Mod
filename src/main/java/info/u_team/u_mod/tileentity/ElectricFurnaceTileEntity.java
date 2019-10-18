package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.container.ElectricFurnaceContainer;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_mod.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.util.LazyOptional;

public class ElectricFurnaceTileEntity extends BasicEnergyTileEntity implements INamedContainerProvider, ITickableTileEntity {
	
	private final LazyOptional<FixedSizeItemStackHandler> ingredient = LazyOptional.of(() -> new FixedSizeItemStackHandler(1));
	private final LazyOptional<FixedSizeItemStackHandler> output = LazyOptional.of(() -> new FixedSizeItemStackHandler(1));
	
	private final RecipeCache<FurnaceRecipe> recipeCache = new RecipeCache<>(IRecipeType.SMELTING, 1);
	
	private int progress;
	private int maxProgress;
	
	@OnlyIn(Dist.CLIENT)
	private float progressPercentage;
	
	public ElectricFurnaceTileEntity() {
		super(UModTileEntityTypes.ENERGY_FURNANCE, 20000, 100, 0);
	}
	
	@Override
	public void tick() {
	}
	
	// NBT
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		super.writeNBT(compound);
		ingredient.ifPresent(handler -> compound.put("ingredient", handler.serializeNBT()));
		output.ifPresent(handler -> compound.put("output", handler.serializeNBT()));
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
		ingredient.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("ingredient")));
		output.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("output")));
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
		ingredient.invalidate();
		output.invalidate();
	}
	
	// Getter
	
	public int getProgress() {
		return progress;
	}
	
	public int getMaxProgress() {
		return maxProgress;
	}
	
	// Client setter and getter
	
	@OnlyIn(Dist.CLIENT)
	public void setProgressPercentage(float progressPercentage) {
		this.progressPercentage = progressPercentage;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float getProgressPercentage() {
		return progressPercentage;
	}
	
}
