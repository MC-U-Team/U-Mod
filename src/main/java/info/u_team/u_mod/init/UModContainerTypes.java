package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.*;
import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = UMod.MODID)
public class UModContainerTypes {
	
	public static final UContainerType<ElectricFurnaceContainer> ELECTRIC_FURNACE = new UContainerType<>("electric_furnace", ElectricFurnaceContainer::new);
	public static final UContainerType<CrusherContainer> CRUSHER = new UContainerType<>("crusher", CrusherContainer::new);
	public static final UContainerType<OreWasherContainer> ORE_WASHER = new UContainerType<>("ore_washer", OreWasherContainer::new);
	
	@SubscribeEvent
	public static void register(Register<ContainerType<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(UMod.MODID, ContainerType.class).forEach(event.getRegistry()::register);
	}
	
}
