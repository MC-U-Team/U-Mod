package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.tileentity.*;
import info.u_team.u_team_core.tileentitytype.UTileEntityType.UBuilder;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class UModTileEntityTypes {
	
	public static final CommonDeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = CommonDeferredRegister.create(ForgeRegistries.TILE_ENTITIES, UMod.MODID);
	
	public static final TileEntityType<ElectricFurnaceTileEntity> ELECTRIC_FURNACE = UBuilder.create("electric_furnace", ElectricFurnaceTileEntity::new, UModBlocks.ELECTRIC_FURNACE).build();
	public static final TileEntityType<CrusherTileEntity> CRUSHER = UBuilder.create("crusher", CrusherTileEntity::new, UModBlocks.CRUSHER).build();
	public static final TileEntityType<OreWasherTileEntity> ORE_WASHER = UBuilder.create("ore_washer", OreWasherTileEntity::new, UModBlocks.ORE_WASHER).build();
	
	public static void register(IEventBus bus) {
		TILE_ENTITY_TYPES.register(bus);
	}
	
}