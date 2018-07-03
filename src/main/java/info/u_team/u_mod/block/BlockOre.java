package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_team_core.api.IMetaType;
import info.u_team.u_team_core.block.UBlockMetaData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockOre extends UBlockMetaData {
	
	public BlockOre(String name, IMetaType[] array) {
		super(name, Material.ROCK, UCreativeTabs.RESOURCES, array);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public void registerModel() {
		for (int i = 0; i < getList().length; i++) {
			setModel(getItem(), i, new ResourceLocation(UConstants.MODID, "ore"));
		}
	}
	
	// We need custom particle here cause else they are not displayed with our color
	// (need to color only the right things)
	
	// TODO when we have time to investigate that :D Or @MrTroble will do this
	// :lazy:
	
	@Override
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
	}
	
	@Override
	public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
		return super.addRunningEffects(state, world, pos, entity);
	}
	
	@Override
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		return super.addHitEffects(state, worldObj, target, manager);
	}
	
	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		return super.addDestroyEffects(world, pos, manager);
	}
	
}
