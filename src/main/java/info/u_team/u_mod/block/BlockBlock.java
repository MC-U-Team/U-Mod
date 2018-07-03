package info.u_team.u_mod.block;

import java.util.Map;

import com.google.common.collect.Maps;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.UBlockMetaData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.*;

public class BlockBlock extends UBlockMetaData {
	
	public BlockBlock(String name, IMetaType[] array) {
		super(name, Material.ROCK, UCreativeTabs.RESOURCES, array);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		for (int i = 0; i < getList().length; i++) {
			setModel(getItem(), i, new ResourceLocation(UConstants.MODID, "block"));
		}
		ModelLoader.setCustomStateMapper(this, new IStateMapper() {
			
			@Override
			public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block block) {
				Map<IBlockState, ModelResourceLocation> models = Maps.newLinkedHashMap();
				block.getBlockState().getValidStates().forEach(state -> models.put(state, new ModelResourceLocation(new ResourceLocation(UConstants.MODID, "block"), "normal")));
				return models;
			}
		});
	}
	
}
