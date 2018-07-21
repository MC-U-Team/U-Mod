package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.machine.BlockMaschine;
import info.u_team.u_mod.tilentity.TileEntityNuclearReactor;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.util.ResourceLocation;


public class BlockNuclearReactor extends BlockMaschine {

	public BlockNuclearReactor(String name) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "nuclear_reactor_tile"), TileEntityNuclearReactor.class));
	}
	
}
