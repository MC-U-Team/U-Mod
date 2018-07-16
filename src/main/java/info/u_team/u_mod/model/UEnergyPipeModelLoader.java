package info.u_team.u_mod.model;

import java.util.ArrayList;

import info.u_team.u_mod.UConstants;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

public class UEnergyPipeModelLoader implements ICustomModelLoader {
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if (modelLocation.getResourceDomain().equals(UConstants.MODID) && modelLocation.getResourcePath().contains("energy_pipe")
		// TODO Fix mich
				&& !modelLocation.getResourcePath().endsWith(".item")) {
			return true;
		}
		return false;
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		if (modelLocation instanceof ModelResourceLocation) {
			ModelResourceLocation loc = (ModelResourceLocation) modelLocation;
			String[] values = loc.getVariant().split(",");
			ArrayList<EnumFacing> face = new ArrayList<>();
			for (String str : values) {
				String[] pair = str.split("=");
				if (Boolean.valueOf(pair[1])) {
					face.add(EnumFacing.byName(pair[0]));
				}
			}
			EnumFacing[] face2 = new EnumFacing[face.size()];
			face.toArray(face2);
			return new UEnergyPipeModel(false, face2);
		}
		// TODO Fix mich
		// return new UEnergyPipeModel(true, new EnumFacing[0] );
		return null;
	}
	
}
