package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.container.*;
import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class UModContainerTypes {
	
	public static final CommonDeferredRegister<ContainerType<?>> CONTAINER_TYPES = CommonDeferredRegister.create(ForgeRegistries.CONTAINERS, UMod.MODID);
	
	public static final UContainerType<ElectricFurnaceContainer> ELECTRIC_FURNACE = new UContainerType<>("electric_furnace", ElectricFurnaceContainer::new);
	public static final UContainerType<CrusherContainer> CRUSHER = new UContainerType<>("crusher", CrusherContainer::new);
	public static final UContainerType<OreWasherContainer> ORE_WASHER = new UContainerType<>("ore_washer", OreWasherContainer::new);
	
	public static void register(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
	
}
