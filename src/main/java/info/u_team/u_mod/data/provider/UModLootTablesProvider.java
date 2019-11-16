package info.u_team.u_mod.data.provider;

import java.util.function.BiConsumer;

import info.u_team.u_mod.init.UModBlocks;
import info.u_team.u_mod.loot.SetTileEntityNBT;
import info.u_team.u_team_core.data.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;

public class UModLootTablesProvider extends CommonLootTablesProvider {
	
	public UModLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(UModBlocks.ELECTRIC_FURNACE, addTileEntityLootTable(UModBlocks.ELECTRIC_FURNACE), consumer);
	}
	
	protected static LootTable addTileEntityLootTable(IItemProvider item) {
		return LootTable.builder() //
				.setParameterSet(LootParameterSets.BLOCK) //
				.addLootPool(LootPool.builder() //
						.rolls(ConstantRange.of(1)) //
						.addEntry(ItemLootEntry.builder(item)) //
						.acceptFunction(SetTileEntityNBT.builder()) //
						.acceptCondition(SurvivesExplosion.builder())) //
				.build();
	}
	
}
