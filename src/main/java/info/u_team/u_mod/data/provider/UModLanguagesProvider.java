package info.u_team.u_mod.data.provider;

import static info.u_team.u_mod.init.UModBlocks.*;

import info.u_team.u_team_core.data.*;

public class UModLanguagesProvider extends CommonLanguagesProvider {
	
	public UModLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void addTranslations() {
		addBlock(ELECTRIC_FURNACE, "Electric Furnace");
		add("container.umod.electric_furnace", "Electric Furnace");
		
		addBlock(CRUSHER, "Crusher");
		add("container.umod.crusher", "Crusher");
		
		addBlock(ORE_WASHER, "Ore Washer");
		add("container.umod.ore_washer", "Ore Washer");
	}
}
