package info.u_team.u_mod.block;

import info.u_team.u_mod.api.machines.ISolarPanel;
import info.u_team.u_mod.init.*;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.util.*;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSolarPanel extends UBlockTileEntity {
	
	protected final ISolarPanel solarpanel;
	
	public BlockSolarPanel(String name, ISolarPanel solarpanel) {
		super(name + "_" + solarpanel.getName(), UModItemGroups.machines, new BlockProperties(Material.IRON, Material.IRON.getColor()).hardnessAndResistance(1F).sound(SoundType.METAL), new ItemProperties().rarity(solarpanel.getRarity()), UModTileEntityTypes.solarpanel);
		this.solarpanel = solarpanel;
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		return openContainer(world, pos, player, true);
	}
	
	public ISolarPanel getSolarpanel() {
		return solarpanel;
	}
	
}
