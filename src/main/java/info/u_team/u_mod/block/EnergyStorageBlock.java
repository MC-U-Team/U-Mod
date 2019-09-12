package info.u_team.u_mod.block;

import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.block.UTileEntityBlock;
import net.minecraft.block.material.Material;

public class EnergyStorageBlock extends UTileEntityBlock{

	public EnergyStorageBlock(String name) {
		super(name, UModItemGroups.GROUP, Properties.create(Material.IRON), () -> UModTileEntityTypes.ENERGYSTORAGE);
		// TODO Auto-generated constructor stub
	}

}
