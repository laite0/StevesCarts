package vswe.stevescarts.renders.model.modularTrack;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;
import vswe.stevescarts.blocks.BlockModularTack;
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
		    if(quadCache.containsKey(state.toString())){
			    return quadCache.get(state.toString());
		    }
		    BlockRailBase.EnumRailDirection direction = state.getValue(BlockModularTack.SHAPE);

		    BlockFaceUV uv = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
		    BlockPartFace face = new BlockPartFace(null, 0, "", uv);
		    ModelRotation modelRot = ModelRotation.X0_Y0;
		    if(direction == BlockRailBase.EnumRailDirection.EAST_WEST){
			    modelRot = ModelRotation.X0_Y90;
		    }

		    if(direction == BlockRailBase.EnumRailDirection.ASCENDING_NORTH){
			    modelRot = ModelRotation.X0_Y180;
		    }
		    if(direction == BlockRailBase.EnumRailDirection.ASCENDING_EAST){
			    modelRot = ModelRotation.X0_Y270;
		    }
		    if(direction == BlockRailBase.EnumRailDirection.ASCENDING_WEST){
			    modelRot = ModelRotation.X0_Y90;
		    }

		    boolean isCorner = false;

		    if(direction == BlockRailBase.EnumRailDirection.SOUTH_EAST){
			    isCorner = true;
		    }
		    if(direction == BlockRailBase.EnumRailDirection.SOUTH_WEST){
			    modelRot = ModelRotation.X0_Y90;
			    isCorner = true;
		    }
		    if(direction == BlockRailBase.EnumRailDirection.NORTH_EAST){
			    modelRot = ModelRotation.X0_Y270;
			    isCorner = true;
		    }
		    if(direction == BlockRailBase.EnumRailDirection.NORTH_WEST){
			    modelRot = ModelRotation.X0_Y180;
			    isCorner = true;
		    }

		    BlockPartRotation partRotation = null;
		    float length = 16F;
		    if(direction.isAscending()){
		    	partRotation = new BlockPartRotation(new Vector3f(0, 0, 0), EnumFacing.Axis.X, -45, false);
		    	length = 23F;
		    }
		    float offset = 0.2F;

		    for(TrackList.TrackModuleType moduleType : TrackList.TrackModuleType.values()){
			    IProperty property = TrackManager.propertyList.get(moduleType);
			    TrackList.TrackModule trackModule = TrackManager.getModuleFromName(state.getValue(property).toString());
			    if(trackModule != null){
				    list.add(faceBakery.makeBakedQuad(
					    new Vector3f(0, 0, 0),
					    new Vector3f(16, offset, length), face, ModularTrackRenderUtils.getTexture(trackModule, isCorner),
					    EnumFacing.UP, modelRot, partRotation, false, true));

				    //TODO render the underside when needed, ie on slops
				    //offset += 0.01f;
			    }
		    }


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
