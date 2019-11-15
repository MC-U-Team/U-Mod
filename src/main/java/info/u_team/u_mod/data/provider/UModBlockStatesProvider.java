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
		addMachine(UModBlocks.ENERGY_FURNACE);
	}
	
	private void addMachine(BasicMachineBlock block) {
		final String path = block.getRegistryName().getPath();
		horizontalBlock(block, cubeFacingBottomTop(path, modBlockLoc(path + "_front"), modBlockLoc(path + "_bottom"), modBlockLoc(path + "_top"), modBlockLoc(path + "_side")));
	}
	
	private ResourceLocation modBlockLoc(String name) {
		return modLoc("block/" + name);
	}
	
}
