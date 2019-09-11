package info.u_team.u_mod.block;

import info.u_team.u_mod.init.UModItemGroups;
import info.u_team.u_team_core.block.UBlock;
import net.minecraft.block.material.Material;

public class PipeBlock extends UBlock{

	public PipeBlock(String name) {
		super(name, UModItemGroups.GROUP, Properties.create(Material.IRON));
	}

	// TODO AABB
	
}
