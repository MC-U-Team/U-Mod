package info.u_team.u_mod.model;

import java.util.*;
import java.util.function.Function;

import com.google.common.collect.*;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.*;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;

public class UEnergyPipeModel implements IModel {
	
	private ResourceLocation particle;
	private ResourceLocation texture;
	
	public UEnergyPipeModel() {
		this.particle = new ResourceLocation("minecraft", "blocks/iron_block");
		this.texture = new ResourceLocation("minecraft", "blocks/iron_block");
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableList.of(this.particle, this.texture);
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new UEnergyPipeBakedModel(format, bakedTextureGetter.apply(particle), bakedTextureGetter.apply(texture), 0xFFFFFF);
	}
	
	public class UEnergyPipeBakedModel implements IBakedModel {
		
		private static final float eps = 1e-3f;
		
		private final EnumMap<EnumFacing, List<BakedQuad>> faceQuads;
		private final TextureAtlasSprite particle;
		private final TextureAtlasSprite texture;
		private final VertexFormat format;
		private final int color;
		
		public UEnergyPipeBakedModel(VertexFormat format, TextureAtlasSprite particle, TextureAtlasSprite texture, int color) {
			this.faceQuads = Maps.newEnumMap(EnumFacing.class);
			for (EnumFacing side : EnumFacing.values()) {
				faceQuads.put(side, Lists.newArrayList());
			}
			this.particle = particle;
			this.format = format;
			this.color = color;
			this.texture = texture;
			
			addCube(0.2F, 0.2F, 0.2F, 0.4F, 0.4F, 0.4F);
			
		}
		
		private void addCube(float x_size, float y_size, float z_size, float x_offset, float y_offset, float z_offset) {
			// Wired matrix
			final float[][] x = { { x_size, x_size, 0, 0 }, { 0, x_size, x_size, 0 }, { 0, x_size, x_size, 0 }, { x_size, x_size, 0, 0 }, { 0, 0, 0, 0 }, { x_size, x_size, x_size, x_size } };
			final float[][] y = { { 0, 0, 0, 0 }, { y_size, y_size, y_size, y_size }, { y_size, y_size, 0, 0 }, { 0, y_size, y_size, 0 }, { 0, y_size, y_size, 0 }, { y_size, y_size, 0, 0 } };
			final float[][] z = { { 0, z_size, z_size, 0 }, { z_size, z_size, 0, 0 }, { 0, 0, 0, 0 }, { z_size, z_size, z_size, z_size }, { z_size, z_size, 0, 0 }, { 0, z_size, z_size, 0 } };
			final float[][] u = { { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 } };
			final float[][] v = { { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 1, 1, 0, 0 } };
			int i = 0;
			for (EnumFacing side : EnumFacing.VALUES) {
				UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
				builder.setQuadOrientation(side);
				builder.setTexture(texture);
				builder.setQuadTint(0);
				for (int j = 0; j < x[i].length; j++) {
					putVertex(builder, side, x_offset + x[i][j], y_offset + y[i][j], z_offset + z[i][j], texture.getInterpolatedU(u[i][j] * 16), texture.getInterpolatedV(v[i][j] * 16));
				}
				List<BakedQuad> quads;
				if ((quads = faceQuads.get(side)) != null)
					quads.add(builder.build());
				else
					faceQuads.put(side, Lists.newArrayList(builder.build()));
				i++;
			}
		}
		
		private void putVertex(UnpackedBakedQuad.Builder builder, EnumFacing side, float x, float y, float z, float u, float v) {
			for (int e = 0; e < format.getElementCount(); e++) {
				switch (format.getElement(e).getUsage()) {
				case POSITION:
					float[] data = new float[] { x - side.getDirectionVec().getX() * eps, y, z - side.getDirectionVec().getZ() * eps, 1 };
					builder.put(e, data);
					break;
				case COLOR:
					builder.put(e, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, ((color >> 24) & 0xFF) / 255f);
					break;
				case UV:
					if (format.getElement(e).getIndex() == 0) {
						builder.put(e, u, v, 0f, 1f);
						break;
					}
				case NORMAL:
					builder.put(e, side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ(), 0f);
					break;
				default:
					builder.put(e);
					break;
				}
			}
		}
		
		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			if (side == null)
				return ImmutableList.of();
			return this.faceQuads.get(side);
		}
		
		@Override
		public boolean isAmbientOcclusion() {
			return true;
		}
		
		@Override
		public boolean isGui3d() {
			return true;
		}
		
		@Override
		public boolean isBuiltInRenderer() {
			return false;
		}
		
		@Override
		public TextureAtlasSprite getParticleTexture() {
			return particle;
		}
		
		@Override
		public ItemOverrideList getOverrides() {
			return ItemOverrideList.NONE;
		}
		
	}
	
}
