package info.u_team.u_mod.tilentity.machine;

import java.util.List;

import info.u_team.u_mod.api.IMachineRecipe;
import info.u_team.u_mod.container.machine.ContainerEnricher;
import info.u_team.u_mod.recipe.RecipeManager;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityEnricher extends TileEntityMachine {
		
	public TileEntityEnricher() {
		super(2, "enricher");
	}
	
	@Override
	public List<IMachineRecipe> getRecipes() {
		return RecipeManager.getEnricherRecipes();
	}
		
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
		return new ContainerEnricher(player, world, pos);
	}
	
}
