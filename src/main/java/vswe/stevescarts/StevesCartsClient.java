package vswe.stevescarts;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import vswe.stevescarts.content.StevesCartsBlocks;

public class StevesCartsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL.block, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.JUNCTION_RAIL.block, RenderLayer.getCutoutMipped());
    }
}
