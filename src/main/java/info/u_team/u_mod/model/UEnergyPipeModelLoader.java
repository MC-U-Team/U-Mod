package info.u_team.u_mod.model;

import info.u_team.u_mod.UConstants;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;

public class UEnergyPipeModelLoader implements ICustomModelLoader {
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
	
	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if (modelLocation.getResourceDomain().equals(UConstants.MODID) && modelLocation.getResourcePath().contains("energy_pipe")) {
			return true;
		}
		return false;
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new UEnergyPipeModel();
	}
	
}
