package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.item.TimeMachineUpgradeItem;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = UMod.MODID)
public class UModItems {
	
	public static final TimeMachineUpgradeItem TIME_TIER_1_MACHINE_UPGRADE = new TimeMachineUpgradeItem("time_tier_1_machine_upgrade", 4, Rarity.UNCOMMON);
	public static final TimeMachineUpgradeItem TIME_TIER_2_MACHINE_UPGRADE = new TimeMachineUpgradeItem("time_tier_2_machine_upgrade", 16, Rarity.RARE);
	public static final TimeMachineUpgradeItem TIME_TIER_3_MACHINE_UPGRADE = new TimeMachineUpgradeItem("time_tier_3_machine_upgrade", 64, Rarity.EPIC);
	
	@SubscribeEvent
	public static void register(Register<Item> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(UMod.MODID, Item.class).forEach(event.getRegistry()::register);
	}
}
