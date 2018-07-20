package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.machine.BlockMaschine;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_mod.tilentity.TileEntityBuilder;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBuilder extends BlockMaschine{

	public BlockBuilder(String name) {
		super(name, Material.IRON, UCreativeTabs.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "builder_tile"), true, TileEntityBuilder.class));
	}
	
}
