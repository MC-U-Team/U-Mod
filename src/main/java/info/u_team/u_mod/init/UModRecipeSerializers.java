package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class UModRecipeSerializers {
	
	public static final CommonDeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UMod.MODID);
	
	public static final RegistryObject<OneIngredientMachineRecipe.Serializer<OneIngredientMachineRecipe>> CRUSHER = RECIPE_SERIALIZERS.register("crusher", () -> new OneIngredientMachineRecipe.Serializer<>(UModRecipeTypes.CRUSHER));
	
	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
	
}
