package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.tileentity.*;
import info.u_team.u_team_core.util.registry.TileEntityTypeDeferredRegister;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class UModTileEntityTypes {
	
	public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = TileEntityTypeDeferredRegister.create(UMod.MODID);
	
	public static final RegistryObject<TileEntityType<EnergyStorageTileEntity>> ENERGY_STORAGE = TILE_ENTITY_TYPES.register("energy_storage", () -> TileEntityType.Builder.create(EnergyStorageTileEntity::new, UModBlocks.ENERGY_STORAGE.get()));
	
	public static final RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE = TILE_ENTITY_TYPES.register("electric_furnace", () -> TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, UModBlocks.ELECTRIC_FURNACE.get()));
	public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER = TILE_ENTITY_TYPES.register("crusher", () -> TileEntityType.Builder.create(CrusherTileEntity::new, UModBlocks.CRUSHER.get()));
	public static final RegistryObject<TileEntityType<OreWasherTileEntity>> ORE_WASHER = TILE_ENTITY_TYPES.register("ore_washer", () -> TileEntityType.Builder.create(OreWasherTileEntity::new, UModBlocks.ORE_WASHER.get()));
	
	public static void register(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}