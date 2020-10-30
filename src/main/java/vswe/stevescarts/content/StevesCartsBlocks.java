package vswe.stevescarts.content;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;

import java.util.Locale;

public enum StevesCartsBlocks implements ItemConvertible {
    CART_ASSEMBLER,
    CARGO_MANAGER,
    LIQUID_MANAGER,

    EXTERNAL_DISTRIBUTOR,
    MODULE_TOGGLER,
    DETECTOR_UNIT,
    DETECTOR_MANAGER,
    DETECTOR_STATION,
    DETECTOR_JUNCTION,
    DETECTOR_REDSTONE_UNIT,

    REINFORCED_METAL_BLOCK,
    GALGADORIAN_BLOCK,
    ENHANCED_GALGADORIAN_BLOCK;

    public final String name;
    public final Block block;

    StevesCartsBlocks(){
        name = this.toString().toLowerCase(Locale.ROOT);
        block = new Block(FabricBlockSettings.of(Material.METAL).strength(2f, 2f).sounds(BlockSoundGroup.METAL));
    }

    @Override
    public Item asItem() {
        return block.asItem();
    }
}
