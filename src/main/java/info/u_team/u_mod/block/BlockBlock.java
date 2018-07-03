package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.UBlockMetaData;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBlock extends UBlockMetaData {
	
	public BlockBlock(String name, IMetaType[] array) {
		super(name, Material.ROCK, UCreativeTabs.RESOURCES, array);
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < getList().length; i++) {
			setModel(getItem(), i, new ResourceLocation(UConstants.MODID, "block"));
		}
	}
	
}
