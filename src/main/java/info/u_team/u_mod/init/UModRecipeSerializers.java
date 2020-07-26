package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_team_core.util.registry.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

public class UModRecipeSerializers {
	
	public static final CommonDeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = CommonDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UMod.MODID);
	
	public static final OneIngredientMachineRecipe.Serializer<OneIngredientMachineRecipe> CRUSHER = new OneIngredientMachineRecipe.Serializer<>("crusher", UModRecipeTypes.CRUSHER);
	
	public static void register(IEventBus bus) {
		RECIPE_SERIALIZERS.register(bus);
	}
	
}
