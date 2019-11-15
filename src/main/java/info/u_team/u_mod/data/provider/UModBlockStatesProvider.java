package info.u_team.u_mod.data.provider;

import info.u_team.u_mod.block.basic.BasicMachineBlock;
import info.u_team.u_mod.init.UModBlocks;
import info.u_team.u_team_core.data.*;
import net.minecraft.util.ResourceLocation;

public class UModBlockStatesProvider extends CommonBlockStatesProvider {
	
	public UModBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		addMachine(UModBlocks.ELECTRIC_FURNACE);
	}
	
	private void addMachine(BasicMachineBlock block) {
		final String path = block.getRegistryName().getPath();
		horizontalBlock(block, cubeFacing(path, modBlockLoc("machine/" + path + "_front"), modBlockLoc("machine/side")));
	}
	
	private ResourceLocation modBlockLoc(String name) {
		return modLoc("block/" + name);
	}
	
}
