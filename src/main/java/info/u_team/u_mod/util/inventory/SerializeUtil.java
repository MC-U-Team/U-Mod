package info.u_team.u_mod.util.inventory;

import com.google.gson.*;

import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class SerializeUtil {
	
	public static JsonElement serializeIngredient(Ingredient ingredient) {
		return ingredient.serialize();
	}
	
	public static Ingredient deserializeIngredient(JsonElement json) {
		return Ingredient.deserialize(json);
	}
	
	public static JsonElement serializeItemStack(ItemStack stack) {
		if (stack.hasTag() || stack.getCount() != 1) {
			return null;
		} else {
			return new JsonPrimitive(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
		}
	}
	
	public static ItemStack deserializeItemStack(JsonElement json) {
		if (json != null && !json.isJsonNull()) {
			if (json.isJsonObject()) {
				return CraftingHelper.getItemStack(json.getAsJsonObject(), true);
			} else if (json.isJsonPrimitive()) {
				final Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsString()));
				if (item == null) {
					throw new IllegalStateException("Item: " + json.getAsString() + " does not exist");
				}
				return new ItemStack(item);
			} else {
				throw new JsonSyntaxException("Expected item to be object or array of objects");
			}
		} else {
			throw new JsonSyntaxException("Item cannot be null");
		}
	}
	
}
