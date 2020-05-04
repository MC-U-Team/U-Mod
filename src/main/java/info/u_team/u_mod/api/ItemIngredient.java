package info.u_team.u_mod.api;

import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

public class ItemIngredient extends Ingredient {
	
	private final int amount;
	
	public static Ingredient fromItems(int amount, IItemProvider... items) {
		return fromItemListStream(amount, Arrays.stream(items).map((item) -> new SingleItemList(new ItemStack(item))));
	}
	
	public static Ingredient fromStacks(int amount, ItemStack... stacks) {
		return fromItemListStream(amount, Arrays.stream(stacks).map((stack) -> new SingleItemList(stack)));
	}
	
	public static Ingredient fromTag(int amount, Tag<Item> tag) {
		return fromItemListStream(amount, Stream.of(new Ingredient.TagList(tag)));
	}
	
	public static Ingredient fromItemListStream(int amount, Stream<? extends IItemList> stream) {
		Ingredient ingredient = new ItemIngredient(amount, stream);
		return ingredient.acceptedItems.length == 0 ? EMPTY : ingredient;
	}
	
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
