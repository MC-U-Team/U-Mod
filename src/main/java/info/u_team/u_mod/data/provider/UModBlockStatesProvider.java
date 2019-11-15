package info.u_team.u_mod.data.provider;

import info.u_team.u_mod.block.basic.BasicMachineBlock;
import info.u_team.u_mod.init.UModBlocks;
import info.u_team.u_team_core.data.*;

public class UModBlockStatesProvider extends CommonBlockStatesProvider {
	
	public UModBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		addMachine(UModBlocks.ENERGY_FURNACE);
	}
	
	private void addMachine(BasicMachineBlock block) {
		final String path = block.getRegistryName().getPath();
		facingBlock(block, cubeFacingBottomTop(path, modLoc(path + "_front"), modLoc(path + "_bottom"), modLoc(path + "_top"), modLoc(path + "_side")));
	}
	
}
