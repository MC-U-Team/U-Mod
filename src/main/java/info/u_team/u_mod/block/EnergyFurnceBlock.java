package info.u_team.u_mod.block;

import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.block.UTileEntityBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class EnergyFurnceBlock extends UTileEntityBlock {
	
	public EnergyFurnceBlock(String name) {
		super(name, UModItemGroups.GROUP, Properties.create(Material.IRON), () -> UModTileEntityTypes.ENERGY_FURNANCE);
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return openContainer(world, pos, player, true);
	}
	
}
