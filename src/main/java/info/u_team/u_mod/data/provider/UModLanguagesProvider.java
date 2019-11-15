package info.u_team.u_mod.data.provider;

import info.u_team.u_mod.init.UModBlocks;
import info.u_team.u_team_core.data.*;

public class UModLanguagesProvider extends CommonLanguagesProvider {
	
	public UModLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void addTranslations() {
		add(UModBlocks.ELECTRIC_FURNACE, "Electric Furnace");
		add("container.umod.electric_furnace", "Electric Furnace");
	}
}
