package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.tileentity.TileEntitySolarPanel;
import info.u_team.u_team_core.registry.TileEntityTypeRegistry;
import info.u_team.u_team_core.tileentitytype.UTileEntityType;
import net.minecraft.tileentity.TileEntityType;

public class UModTileEntityTypes {
	
	public static final TileEntityType<TileEntitySolarPanel> solarpanel = UTileEntityType.Builder.create("solarpanel", TileEntitySolarPanel::new).build();
	
	public static void construct() {
		TileEntityTypeRegistry.register(UMod.modid, UModTileEntityTypes.class);
	}
	
}
