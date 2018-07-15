package info.u_team.u_mod.model;

import java.util.*;
import java.util.function.Function;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.*;
import com.google.common.graph.ImmutableValueGraph;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.block.EnergyPipeBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.*;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelFluid;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class UEnergyPipeModel implements IModel {
	
	private ResourceLocation particle;
	private ResourceLocation texture;
	private boolean isItem;
	private EnumFacing[] enabled;
	
	public UEnergyPipeModel(boolean isItem, EnumFacing[] enabled) {
		this.particle = new ResourceLocation("minecraft", "blocks/iron_block");
		this.texture = new ResourceLocation("minecraft", "blocks/iron_block");
		this.isItem = isItem;
		this.enabled = enabled;
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableList.of(this.particle, this.texture);
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableMap<TransformType, TRSRTransformation> map = PerspectiveMapWrapper.getTransforms(state);
		return new UEnergyPipeBakedModel(this.enabled, this.isItem, state.apply(Optional.empty()), map, format, bakedTextureGetter.apply(particle), bakedTextureGetter.apply(texture), 0xFFFFFF);
	}
	
	public class UEnergyPipeBakedModel implements IBakedModel {
		
		private static final float eps = 1e-3f;
		
		private final EnumMap<EnumFacing, List<BakedQuad>> faceQuads;
		private final TextureAtlasSprite particle;
		private final TextureAtlasSprite texture;
		private final VertexFormat format;
		private final Optional<TRSRTransformation> transformation;
		private final int color;
		private final boolean isItem;
		private final ImmutableMap<TransformType, TRSRTransformation> transforms;
		private EnumFacing[] enabled;

		public UEnergyPipeBakedModel(EnumFacing[] enabled, boolean isItem, Optional<TRSRTransformation> transformation, ImmutableMap<TransformType, TRSRTransformation> map, VertexFormat format, TextureAtlasSprite particle, TextureAtlasSprite texture, int color) {
			this.transformation = transformation;
			this.enabled = enabled;
			this.isItem = isItem;
			this.transforms = map;
			this.faceQuads = Maps.newEnumMap(EnumFacing.class);
			for (EnumFacing enumFacing : EnumFacing.VALUES) {
				this.faceQuads.put(enumFacing, Lists.newArrayList());
			}
			this.particle = particle;
			this.format = format;
			this.color = color;
			this.texture = texture;
			
			addCube(0.2F, 0.2F, 0.2F, 0.4F, 0.4F, 0.4F);

			if (!this.isItem) {
				for (EnumFacing enumFacing : enabled) {
					switch(enumFacing) {
					case UP:
						addCube(0.2F, 0.4F, 0.2F, 0.4F, 0.6F, 0.4F);
						break;
					case DOWN:
						addCube(0.2F, 0.4F, 0.2F, 0.4F, 0.0F, 0.4F);
						break;
					case NORTH:
						addCube(0.2F, 0.2F, 0.4F, 0.4F, 0.4F, 0.6F);
						break;
					case SOUTH:
						addCube(0.2F, 0.2F, 0.4F, 0.4F, 0.4F, 0.0F);
						break;
					case EAST:
						addCube(0.4F, 0.2F, 0.2F, 0.6F, 0.4F, 0.4F);
						break;
					case WEST:
						addCube(0.4F, 0.2F, 0.2F, 0.0F, 0.4F, 0.4F);
						break;
					default:
						break;
					
					}
				}
			}
		}
				
		private void addCube(float x_size, float y_size, float z_size, float x_offset, float y_offset, float z_offset) {
			// dumb matrix
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
					if (transformation.isPresent() && !transformation.get().isIdentity()) {
						Vector4f vec = new Vector4f(data);
						transformation.get().getMatrix().transform(vec);
						vec.get(data);
					}
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
			if (side == null) {
				ArrayList<BakedQuad> quads = Lists.newArrayList();
				for(EnumFacing face : EnumFacing.VALUES) {
					quads.addAll(this.faceQuads.get(face));
				}
				return faceQuads.get(EnumFacing.UP);
			}
			return faceQuads.get(side);
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
				
		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
			return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
		}
		
	}
	
}
