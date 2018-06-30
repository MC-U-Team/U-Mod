package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_mod.model.UEnergyPipeModelLoader;
import info.u_team.u_mod.tilentity.IEnergyStorageProvider;
import info.u_team.u_mod.tilentity.TileEntityEnergyPipe;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class EnergyPipeBlock extends UBlockTileEntity {
	
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");
	
	private static PropertyBool[] _properties = new PropertyBool[] { UP, DOWN, NORTH, SOUTH, EAST, WEST };
	
	public EnergyPipeBlock(String name) {
		super(name, Material.IRON, UCreativeTabs.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "energy_pipe_tile"), true, TileEntityEnergyPipe.class));
		this.setDefaultState(this.getDefaultState().withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(EAST, false).withProperty(WEST, false));
	}
	
	@Override
	public void registerModel() {
		ModelLoaderRegistry.registerLoader(new UEnergyPipeModelLoader());
		super.registerModel();
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int i = 0;
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity entity = worldIn.getTileEntity(pos.offset(face.getOpposite()));
			if (entity != null && entity instanceof IEnergyStorageProvider) {
				state = state.withProperty(_properties[i], true);
			} else {
				state = state.withProperty(_properties[i], false);
			}
			i++;
		}
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, _properties);
	}
}
