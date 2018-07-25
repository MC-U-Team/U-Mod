package info.u_team.u_mod.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;
import com.ibm.icu.impl.Assert;

import info.u_team.u_mod.UConstants;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

public class UModelLoader implements ICustomModelLoader {
	
	private static HashMap<String, Class<? extends IModel>> models = Maps.newHashMap();
	
	public static void register(String name, Class<? extends IModel> clazz) {
		models.put(name, clazz);
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if (modelLocation.getResourceDomain().equals(UConstants.MODID)) {
			if(models.containsKey(modelLocation.getResourcePath())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		if (modelLocation instanceof ModelResourceLocation) {
			return models.get(modelLocation.getResourcePath()).getConstructor(ModelResourceLocation.class).newInstance((ModelResourceLocation)modelLocation);
		}
		return null;
	}
	
}
