package info.u_team.u_mod.block;

import java.util.List;

import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IUMetaType;
import info.u_team.u_team_core.block.UBlockMetaData;
import net.minecraft.block.material.Material;

public class BlockOre extends UBlockMetaData {
	
	public BlockOre(String name, List<IUMetaType> list) {
		super(name, Material.ROCK, UCreativeTabs.RESOURCES, list);
	}
	
}
