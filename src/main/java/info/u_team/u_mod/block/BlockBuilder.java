package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.machine.BlockMachine;
import info.u_team.u_mod.tilentity.TileEntityBuilder;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.util.ResourceLocation;

public class BlockBuilder extends BlockMachine{

	public BlockBuilder(String name) {
		super(name, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "builder_tile"), true, TileEntityBuilder.class));
	}
	
}
