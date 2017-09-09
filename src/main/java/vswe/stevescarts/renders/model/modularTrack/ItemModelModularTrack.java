package vswe.stevescarts.renders.model.modularTrack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import vswe.stevescarts.blocks.ModBlocks;
import vswe.stevescarts.renders.model.ModelHelper;
import vswe.stevescarts.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class ItemModelModularTrack implements IModel {

	public static final ItemModelModularTrack MODEL = new ItemModelModularTrack(
		Collections.singletonList(new ResourceLocation("stevescarts:blocks/modulartrack/wooden_rail"))
	);
	public static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(ModBlocks.MODULAR_RAIL.getBlock().getRegistryName(), "default");
	public static final ItemModelModularTrack.OverrideHandler OVERRIDES = new ItemModelModularTrack.OverrideHandler();
	public List<ResourceLocation> textures;

	public ItemModelModularTrack(List<ResourceLocation> textures) {
		this.textures = textures;
	}

	public static void init() {
		ModelLoader.setCustomMeshDefinition(ModBlocks.MODULAR_RAIL.getItem(), stack -> MODEL_LOCATION);
		ModelBakery.registerItemVariants(ModBlocks.MODULAR_RAIL.getItem(), MODEL_LOCATION);
		ModelLoaderRegistry.registerLoader(new DynamicTrackLoader());
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);
		TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());

		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		textures.forEach(resourceLocation -> builder.addAll(new ItemLayerModel(ImmutableList.of(resourceLocation)).bake(transform, format, bakedTextureGetter).getQuads(null, null, 0L)));

		return new BakedTrackItemModel(builder.build(), bakedTextureGetter.apply(textures.get(0)), format, transformMap);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return textures;
	}

	public class BakedTrackItemModel implements IBakedModel {

		private final List<BakedQuad> quads;
		private final TextureAtlasSprite particle;
		private final VertexFormat format;
		private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap;

		public BakedTrackItemModel(List<BakedQuad> quads,
		                           TextureAtlasSprite particle,
		                           VertexFormat format,
		                           ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap) {
			this.quads = quads;
			this.particle = particle;
			this.format = format;
			this.transformMap = transformMap;
		}

		@Override
		public List<BakedQuad> getQuads(
			@Nullable
				IBlockState state,
			@Nullable
				EnumFacing side, long rand) {
			return quads;
		}

		@Override
		public boolean isAmbientOcclusion() {
			return true;
		}

		@Override
		public boolean isGui3d() {
			return false;
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
			return OVERRIDES;
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms() {
			return ModelHelper.DEFAULT_ITEM_TRANSFORMS;
		}
	}

	public static class DynamicTrackLoader implements ICustomModelLoader {

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals("stevescarts") && modelLocation.getResourcePath().contains("modulartrack");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) throws Exception {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

	}

	public static class OverrideHandler extends ItemOverrideList {

		private final HashMap<String, IBakedModel> modelCache = new HashMap<>();

		private final Function<ResourceLocation, TextureAtlasSprite> textureGetter = location ->
			Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

		private OverrideHandler() {
			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel,
		                                   ItemStack stack,
		                                   @Nullable
			                                   World world,
		                                   @Nullable
			                                   EntityLivingBase entity) {
			String name = stack.getTagCompound().toString();
			if (!modelCache.containsKey(name)) {
				List<ResourceLocation> textures = new ArrayList<>();
				List<TrackList.TrackModule> moduleList = TrackManager.fromNBT(stack.getTagCompound());
				moduleList.forEach(trackModule -> textures.add(new ResourceLocation(trackModule.textureLocation)));
				BakedTrackItemModel bakedTrackItemModel = (BakedTrackItemModel) originalModel;
				ItemModelModularTrack model = new ItemModelModularTrack(textures);
				modelCache.put(name, model.bake(new SimpleModelState(bakedTrackItemModel.transformMap), bakedTrackItemModel.format, textureGetter));
			}
			return modelCache.get(name);
		}
	}
}
