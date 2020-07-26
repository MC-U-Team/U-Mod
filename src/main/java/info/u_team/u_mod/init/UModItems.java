package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.item.TimeMachineUpgradeItem;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class UModItems {
	
	public static final CommonDeferredRegister<Item> ITEMS = CommonDeferredRegister.create(ForgeRegistries.ITEMS, UMod.MODID);
	
	public static final TimeMachineUpgradeItem TIME_TIER_1_MACHINE_UPGRADE = new TimeMachineUpgradeItem("time_tier_1_machine_upgrade", 4, Rarity.UNCOMMON);
	public static final TimeMachineUpgradeItem TIME_TIER_2_MACHINE_UPGRADE = new TimeMachineUpgradeItem("time_tier_2_machine_upgrade", 16, Rarity.RARE);
	public static final TimeMachineUpgradeItem TIME_TIER_3_MACHINE_UPGRADE = new TimeMachineUpgradeItem("time_tier_3_machine_upgrade", 64, Rarity.EPIC);
	
	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}
}
