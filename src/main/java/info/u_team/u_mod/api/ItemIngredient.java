package info.u_team.u_mod.api;

import java.util.Arrays;
import java.util.stream.Stream;

import com.google.gson.*;

import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.crafting.*;

public class ItemIngredient extends Ingredient {
	
	private final int amount;
	
	public static ItemIngredient fromItems(int amount, IItemProvider... items) {
		return new ItemIngredient(amount, Arrays.stream(items).map((item) -> new SingleItemList(new ItemStack(item))));
	}
	
	public static ItemIngredient fromStacks(int amount, ItemStack... stacks) {
		return new ItemIngredient(amount, Arrays.stream(stacks).map((stack) -> new SingleItemList(stack)));
	}
	
	public static ItemIngredient fromTag(int amount, Tag<Item> tag) {
		return new ItemIngredient(amount, Stream.of(new Ingredient.TagList(tag)));
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
	
	@Override
	public IIngredientSerializer<? extends Ingredient> getSerializer() {
		return Serializer.INSTANCE;
	}
	
	@Override
	public JsonElement serialize() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
		jsonObject.addProperty("amount", amount);
		jsonObject.add("items", super.serialize());
		return jsonObject;
	}
	
	public static class Serializer implements IIngredientSerializer<ItemIngredient> {
		
		public static final Serializer INSTANCE = new Serializer();
		
		@Override
		public ItemIngredient parse(JsonObject json) {
			return null;
		}
		
		@Override
		public ItemIngredient parse(PacketBuffer buffer) {
			final int amount = buffer.readInt();
			final int length = buffer.readVarInt();
			
			return new ItemIngredient(amount, Stream.generate(() -> new SingleItemList(buffer.readItemStack())).limit(length));
		}
		
		@Override
		public void write(PacketBuffer buffer, ItemIngredient ingredient) {
			final ItemStack[] items = ingredient.getMatchingStacks();
			buffer.writeInt(ingredient.amount);
			buffer.writeVarInt(items.length);
			
			for (ItemStack stack : items) {
				buffer.writeItemStack(stack);
			}
		}
	}
}
