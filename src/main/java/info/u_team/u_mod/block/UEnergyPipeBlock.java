package info.u_team.u_mod.block;

import java.util.List;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.api.ICable;
import info.u_team.u_mod.api.ICableExceptor;
import info.u_team.u_mod.api.IEnergyStorageProvider;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_mod.model.UEnergyPipeModelLoader;
import info.u_team.u_mod.tilentity.UEnergyPipeTile;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class UEnergyPipeBlock extends UBlockTileEntity {
	
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");
	
	private static PropertyBool[] properties = new PropertyBool[] { UP, DOWN, NORTH, SOUTH, EAST, WEST };
	
	public UEnergyPipeBlock(String name) {
		super(name, Material.IRON, UCreativeTabs.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "energy_pipe_tile"), true, UEnergyPipeTile.class));
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
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		state = getActualState(state, worldIn, pos);
		if (state.getValue(UP)) {
			AxisAlignedBB axis = new AxisAlignedBB(0.4, 0.6, 0.4, 0.6, 1, 0.6).offset(pos);
			if (entityBox.intersects(axis)) {
				collidingBoxes.add(axis);
			}
		}
		if (state.getValue(DOWN)) {
			AxisAlignedBB axis = new AxisAlignedBB(0.4, 0, 0.4, 0.6, 0.4, 0.6).offset(pos);
			if (entityBox.intersects(axis)) {
				collidingBoxes.add(axis);
			}
		}
		if (state.getValue(NORTH)) {
			AxisAlignedBB axis = new AxisAlignedBB(0.4, 0.4, 0.6, 0.6, 0.6, 1).offset(pos);
			if (entityBox.intersects(axis)) {
				collidingBoxes.add(axis);
			}
		}
		if (state.getValue(SOUTH)) {
			AxisAlignedBB axis = new AxisAlignedBB(0.4, 0.4, 0, 0.6, 0.6, 0.4).offset(pos);
			if (entityBox.intersects(axis)) {
				collidingBoxes.add(axis);
			}
		}
		if (state.getValue(EAST)) {
			AxisAlignedBB axis = new AxisAlignedBB(0.6, 0.4, 0.4, 1, 0.6, 0.6).offset(pos);
			if (entityBox.intersects(axis)) {
				collidingBoxes.add(axis);
			}
		}
		if (state.getValue(WEST)) {
			AxisAlignedBB axis = new AxisAlignedBB(0, 0.4, 0.4, 0.4, 0.6, 0.6).offset(pos);
			if (entityBox.intersects(axis)) {
				collidingBoxes.add(axis);
			}
		}
		AxisAlignedBB axis = new AxisAlignedBB(0.4, 0.4, 0.4, 0.6, 0.6, 0.6).offset(pos);
		if (entityBox.intersects(axis)) {
			collidingBoxes.add(axis);
		}
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		double anfangY = 0.4F, endeY = 0.6F, anfangX = 0.4F, endeX = 0.6F, anfangZ = 0.4F, endeZ = 0.6F;
		state = getActualState(state, source, pos);
		
		if (state.getValue(UP)) {
			endeY = 1;
		}
		if (state.getValue(DOWN)) {
			anfangY = 0;
		}
		if (state.getValue(EAST)) {
			endeX = 1;
		}
		if (state.getValue(WEST)) {
			anfangX = 0;
		}
		if (state.getValue(NORTH)) {
			endeZ = 1;
		}
		if (state.getValue(SOUTH)) {
			anfangZ = 0;
		}		
		return new AxisAlignedBB(anfangX, anfangY, anfangZ, endeX, endeY, endeZ);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int i = 0;
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity entity = worldIn.getTileEntity(pos.offset(face.getOpposite()));
			if (entity != null && (entity instanceof ICableExceptor || entity instanceof ICable)) {
				state = state.withProperty(properties[i], true);
			} else {
				state = state.withProperty(properties[i], false);
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
		return new BlockStateContainer(this, properties);
	}
}
