package info.u_team.u_mod.particle;

import javax.annotation.Nullable;

import info.u_team.u_mod.UConstants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class ParticleOreDigging extends Particle {
	
	private final IBlockState sourceState;
	private BlockPos sourcePos;
	
	private TextureAtlasSprite texture_stone, texture_ore;
	
	private float particleRedColor, particleGreenColor, particleBlueColor;
	
	public ParticleOreDigging(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
		sourceState = state;
		particleGravity = state.getBlock().blockParticleGravity;
		particleRed = 0.6F;
		particleGreen = 0.6F;
		particleBlue = 0.6F;
		particleScale /= 2.0F;
		
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		texture_stone = map.getAtlasSprite(UConstants.MODID + ":blocks/ore_stone");
		texture_ore = map.getAtlasSprite(UConstants.MODID + ":blocks/ore_overlay");
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		draw(texture_stone, buffer, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		float particleRedCache = particleRed;
		float particleGreenCache = particleGreen;
		float particleBlueCache = particleBlue;
		
		particleRed = particleRedColor;
		particleGreen = particleGreenColor;
		particleBlue = particleBlueColor;
		draw(texture_ore, buffer, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		
		particleRed = particleRedCache;
		particleGreen = particleGreenCache;
		particleBlue = particleBlueCache;
		
	}
	
	public void draw(TextureAtlasSprite sprite, BufferBuilder buffer, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
		float f1 = f + 0.015609375F;
		float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
		float f3 = f2 + 0.015609375F;
		float f4 = 0.1F * this.particleScale;
		
		f = sprite.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
		f1 = sprite.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
		f2 = sprite.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
		f3 = sprite.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
		
		float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
		float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
		float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
		int i = this.getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		buffer.pos(f5 - rotationX * f4 - rotationXY * f4, f6 - rotationZ * f4, f7 - rotationYZ * f4 - rotationXZ * f4).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
		buffer.pos(f5 - rotationX * f4 + rotationXY * f4, f6 + rotationZ * f4, f7 - rotationYZ * f4 + rotationXZ * f4).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
		buffer.pos(f5 + rotationX * f4 + rotationXY * f4, f6 + rotationZ * f4, f7 + rotationYZ * f4 + rotationXZ * f4).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
		buffer.pos(f5 + rotationX * f4 - rotationXY * f4, f6 - rotationZ * f4, f7 + rotationYZ * f4 - rotationXZ * f4).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
	}
	
	public ParticleOreDigging setBlockPos(BlockPos pos) {
		sourcePos = pos;
		multiplyColor(pos);
		return this;
	}
	
	public ParticleOreDigging init() {
		sourcePos = new BlockPos(posX, posY, posZ);
		multiplyColor(sourcePos);
		return this;
	}
	
	protected void multiplyColor(@Nullable BlockPos pos) {
		int i = Minecraft.getMinecraft().getBlockColors().colorMultiplier(sourceState, world, pos, 0);
		particleRedColor = particleRed * (i >> 16 & 255) / 255.0F;
		particleGreenColor = particleGreen * (i >> 8 & 255) / 255.0F;
		particleBlueColor = particleBlue * (i & 255) / 255.0F;
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
	
	@Override
	public int getBrightnessForRender(float partialTicks) {
		int i = super.getBrightnessForRender(partialTicks);
		int j = 0;
		if (world.isBlockLoaded(sourcePos)) {
			j = this.world.getCombinedLight(sourcePos, 0);
		}
		return i == 0 ? j : i;
	}
	
	@SideOnly(Side.CLIENT)
	public static class Factory implements IParticleFactory {
		
		@Override
		public Particle createParticle(int id, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameter) {
			return (new ParticleOreDigging(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, Block.getStateById(parameter[0]))).init();
		}
	}
	
}
