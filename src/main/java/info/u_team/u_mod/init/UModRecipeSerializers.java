package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = UMod.MODID)
public class UModRecipeSerializers {
	
	public static final OneIngredientMachineRecipe.Serializer<OneIngredientMachineRecipe> CRUSHER = new OneIngredientMachineRecipe.Serializer<>("crusher", UModRecipeTypes.CRUSHER);
	
	@SubscribeEvent
	public static void register(Register<IRecipeSerializer<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(UMod.MODID, IRecipeSerializer.class).forEach(event.getRegistry()::register);
	}
	
}
