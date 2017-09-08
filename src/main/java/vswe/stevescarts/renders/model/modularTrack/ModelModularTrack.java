package vswe.stevescarts.renders.model.modularTrack;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.lwjgl.util.vector.Vector3f;
import vswe.stevescarts.blocks.BlockModularTack;
import vswe.stevescarts.blocks.ModBlocks;
import vswe.stevescarts.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import javax.annotation.Nullable;
import java.util.*;

public class ModelModularTrack implements IBakedModel {

	static HashMap<String, List<BakedQuad>> quadCache = new HashMap<>();

	private FaceBakery faceBakery = new FaceBakery();

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {

	    ArrayList<BakedQuad> list = new ArrayList<>();

    	if(side == null){
//		    if(quadCache.containsKey(state.toString())){
//			    return quadCache.get(state.toString());
//		    }
		    BlockFaceUV uv = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
		    BlockPartFace face = new BlockPartFace(null, 0, "", uv);
		    ModelRotation modelRot = ModelRotation.X0_Y0;

		    Arrays.stream(TrackList.TrackModuleType.values()).forEach(moduleType -> {
				    IProperty property = TrackManager.propertyList.get(moduleType);
			    TrackList.TrackModule trackModule = TrackManager.getModuleFromName(state.getValue(property).toString());
			    if(trackModule != null){
				    list.add(faceBakery.makeBakedQuad(
					    new Vector3f(0, 0, 0),
					    new Vector3f(16, 0, 16), face, ModularTrackRenderUtils.getTexture(trackModule),
					    EnumFacing.UP, modelRot, null, false, true));
			    }
		    });



		    quadCache.put(state.toString(), list);
	    }
        return list;
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
        return null;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
