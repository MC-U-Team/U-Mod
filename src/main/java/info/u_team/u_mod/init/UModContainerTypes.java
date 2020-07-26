package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.*;
import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class UModContainerTypes {
	
	public static final CommonDeferredRegister<ContainerType<?>> CONTAINER_TYPES = CommonDeferredRegister.create(ForgeRegistries.CONTAINERS, UMod.MODID);
	
	public static final RegistryObject<UContainerType<ElectricFurnaceContainer>> ELECTRIC_FURNACE = CONTAINER_TYPES.register("electric_furnace", () -> new UContainerType<>(ElectricFurnaceContainer::new));
	public static final RegistryObject<UContainerType<CrusherContainer>> CRUSHER = CONTAINER_TYPES.register("crusher", () -> new UContainerType<>(CrusherContainer::new));
	public static final RegistryObject<UContainerType<OreWasherContainer>> ORE_WASHER = CONTAINER_TYPES.register("ore_washer", () -> new UContainerType<>(OreWasherContainer::new));
	
	public static void register(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
	
}
