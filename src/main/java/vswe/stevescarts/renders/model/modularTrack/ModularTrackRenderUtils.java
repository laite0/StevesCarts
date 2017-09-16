package vswe.stevescarts.renders.model.modularTrack;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import prospector.shootingstar.model.ModelMethods;
import vswe.stevescarts.blocks.BlockModularTack;
import vswe.stevescarts.blocks.ModBlocks;
import vswe.stevescarts.renders.model.ModelGenerator;
import vswe.stevescarts.api.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
public class ModularTrackRenderUtils {

    public static HashMap<String, TextureAtlasSprite> textures = new HashMap<>();

    public static void preinit(){
	    IProperty[] properties = TrackManager.propertyList.values().toArray(new IProperty[TrackManager.propertyList.values().size() + 1]);
	    properties[TrackManager.propertyList.values().size()] = BlockModularTack.SHAPE;
	    ModelMethods.setBlockStateMapper(ModBlocks.MODULAR_RAIL.getBlock(), properties);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) {
        textures.clear();
        ModelModularTrack.quadCache.clear();
        TextureMap textureMap = event.getMap();
	    TrackManager.trackList.modules.forEach(trackModule -> {
		    loadTexture(trackModule.textureLocation, textureMap);
		    loadTexture(trackModule.textureLocationCorner, textureMap);
	    });
    }

    private static void loadTexture(String textureLocation, TextureMap textureMap){
	    TextureAtlasSprite texture = textureMap.getTextureExtry(textureLocation);
	    if (texture == null && !textures.containsKey(textureLocation)) {
		    texture = new ModelGenerator.CustomTexture(textureLocation);
		    textureMap.setTextureEntry(texture);
		    textures.put(textureLocation, texture);
	    }
    }


    public static TextureAtlasSprite getTexture(TrackList.TrackModule trackModule, boolean corner){
    	if(corner){
		    if(!textures.containsKey(trackModule.textureLocationCorner)){
			    //TODO missing texture
		    }
		    return textures.get(trackModule.textureLocationCorner);
	    } else {
		    if(!textures.containsKey(trackModule.textureLocation)){
			    //TODO missing texture
		    }
		    return textures.get(trackModule.textureLocation);
	    }
    }

	@SubscribeEvent()
	public static void bakeModels(ModelBakeEvent event) {
		event.getModelRegistry().putObject(new ModelResourceLocation("stevescarts:modulartrack/modulartrack#normal"), new ModelModularTrack());
	}


	public static ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()), (new DefaultStateMapper()).getPropertyString(state.getProperties()));
	}


}
