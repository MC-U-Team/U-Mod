package info.u_team.u_mod.api;

import java.util.stream.Stream;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ItemIngredient extends Ingredient {
	
	private final int amount;
	
	protected ItemIngredient(int amount, Stream<? extends IItemList> stream) {
		super(stream);
		this.amount = amount;
	}
	
	@Override
	public boolean test(ItemStack stack) {
		if (stack == null) {
			return false;
		} else if (acceptedItems.length == 0) {
			return stack.isEmpty();
		} else {
			determineMatchingStacks();
			for (ItemStack itemstack : matchingStacks) {
				if (itemstack.getItem() == stack.getItem()) {
					return stack.getCount() >= amount;
				}
			}
			return false;
		}
	}
}
