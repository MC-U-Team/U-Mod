package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.UBlockMetaData;
import net.minecraft.block.material.Material;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockOre extends UBlockMetaData {
	
	public BlockOre(String name, IMetaType[] array) {
		super(name, Material.ROCK, UCreativeTabs.RESOURCES, array);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < getList().length; i++) {
			setModel(getItem(), i, new ResourceLocation(UConstants.MODID, "ore"));
		}
	}
	
}
