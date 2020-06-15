package info.u_team.u_mod.tileentity;

import java.util.*;

import info.u_team.u_mod.container.OreWasherContainer;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeData;
import info.u_team.u_team_core.inventory.UFluidStackHandler;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class OreWasherTileEntity extends BasicMachineTileEntity<OneIngredientMachineRecipe> {
	
	protected final UFluidStackHandler fluidIngredientSlots;
	
	protected final LazyOptional<UFluidStackHandler> fluidIngredientSlotsOptional;
	
	public OreWasherTileEntity() {
		super(UModTileEntityTypes.ORE_WASHER, 20000, 100, 0, UModRecipeTypes.CRUSHER, 1, 6, 3, RecipeData.getBasicMachine());
		
		fluidIngredientSlots = new UFluidStackHandler(1);
		
		fluidIngredientSlotsOptional = LazyOptional.of(() -> fluidIngredientSlots);
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.umod.crusher");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new OreWasherContainer(id, playerInventory, this);
	}
	
	// TODO test
	public UFluidStackHandler getFluidIngredientSlots() {
		return fluidIngredientSlots;
	}
	
	int i = 0;
	
	@Override
	protected void tickServer() {
		super.tickServer();
		if (i % 50 == 0) {
			fluidIngredientSlots.setFluidInTank(0, new FluidStack(choice(ForgeRegistries.FLUIDS.getValues(), new Random()), 1000));
		}
		i++;
	}
	
	public static <E> E choice(Collection<? extends E> coll, Random rand) {
		if (coll.size() == 0) {
			return null;
		}
		
		int index = rand.nextInt(coll.size());
		if (coll instanceof List) {
			return ((List<? extends E>) coll).get(index);
		} else {
			Iterator<? extends E> iter = coll.iterator();
			for (int i = 0; i < index; i++) {
				iter.next();
			}
			return iter.next();
		}
	}
}
