package info.u_team.u_mod.block;

import java.util.*;

import com.google.common.collect.Maps;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.particle.ParticleOreDigging;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.UBlockMetaData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.*;

public class BlockOre extends UBlockMetaData {
	
	public BlockOre(String name, IMetaType[] array) {
		super(name, Material.ROCK, UCreativeTabs.resources, array);
		setHardness(5);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		for (int i = 0; i < getList().length; i++) {
			setModel(getItem(), i, new ResourceLocation(UConstants.MODID, "ore"));
		}
		ModelLoader.setCustomStateMapper(this, new IStateMapper() {
			
			@Override
			public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block block) {
				Map<IBlockState, ModelResourceLocation> models = Maps.newLinkedHashMap();
				block.getBlockState().getValidStates().forEach(state -> models.put(state, new ModelResourceLocation(new ResourceLocation(UConstants.MODID, "ore"), "normal")));
				return models;
			}
		});
	}
	
	// Bad and confusing code, but that are just vanilla methods for our custom
	// particle
	
	@Override
	public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos pos, IBlockState state2, EntityLivingBase entity, int numberOfParticles) {
		if (world instanceof WorldServer) {
			world.spawnParticle(UParticles.oredust, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, Block.getStateId(state));
		}
		return true;
	}
	
	@Override
	public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
		Random rand = new Random();
		double posX = entity.posX;
		double posZ = entity.posZ;
		world.spawnParticle(UParticles.oredigging, posX + (rand.nextFloat() - 0.5D) * entity.width, entity.getEntityBoundingBox().minY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * entity.width, -entity.motionX * 4.0D, 1.5D, -entity.motionZ * 4.0D, Block.getStateId(state));
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
		EnumFacing side = target.sideHit;
		BlockPos pos = target.getBlockPos();
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		AxisAlignedBB axisalignedbb = state.getBoundingBox(world, pos);
		Random rand = new Random();
		double d0 = i + rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minX;
		double d1 = j + rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minY;
		double d2 = k + rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minZ;
		
		if (side == EnumFacing.DOWN) {
			d1 = j + axisalignedbb.minY - 0.10000000149011612D;
		}
		
		if (side == EnumFacing.UP) {
			d1 = j + axisalignedbb.maxY + 0.10000000149011612D;
		}
		
		if (side == EnumFacing.NORTH) {
			d2 = k + axisalignedbb.minZ - 0.10000000149011612D;
		}
		
		if (side == EnumFacing.SOUTH) {
			d2 = k + axisalignedbb.maxZ + 0.10000000149011612D;
		}
		
		if (side == EnumFacing.WEST) {
			d0 = i + axisalignedbb.minX - 0.10000000149011612D;
		}
		
		if (side == EnumFacing.EAST) {
			d0 = i + axisalignedbb.maxX + 0.10000000149011612D;
		}
		
		manager.addEffect((new ParticleOreDigging(world, d0, d1, d2, 0.0D, 0.0D, 0.0D, state)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		IBlockState state = world.getBlockState(pos);
		for (int j = 0; j < 4; ++j) {
			for (int k = 0; k < 4; ++k) {
				for (int l = 0; l < 4; ++l) {
					double d0 = (j + 0.5D) / 4.0D;
					double d1 = (k + 0.5D) / 4.0D;
					double d2 = (l + 0.5D) / 4.0D;
					manager.addEffect((new ParticleOreDigging(world, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, state)).setBlockPos(pos));
				}
			}
		}
		return true;
	}
	
}
