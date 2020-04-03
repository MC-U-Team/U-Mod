package info.u_team.u_mod.data.provider;

import static info.u_team.u_mod.init.UModBlocks.*;

import info.u_team.u_team_core.data.*;

public class UModItemModelsProvider extends CommonItemModelsProvider {
	
	public UModItemModelsProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerModels() {
		simpleBlock(ELECTRIC_FURNACE);
		simpleBlock(CRUSHER);
	}
}
