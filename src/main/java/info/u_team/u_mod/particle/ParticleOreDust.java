package info.u_team.u_mod.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class ParticleOreDust extends ParticleOreDigging {
	
	public ParticleOreDust(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, state);
		this.motionX = xSpeed;
		this.motionY = ySpeed;
		this.motionZ = zSpeed;
	}
	
	@SideOnly(Side.CLIENT)
	public static class Factory implements IParticleFactory {
		
		@Override
		public Particle createParticle(int id, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameter) {
			return (new ParticleOreDust(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, Block.getStateById(parameter[0]))).init();
		}
	}
}
