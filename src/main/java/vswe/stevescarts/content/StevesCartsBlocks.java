package vswe.stevescarts.content;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import vswe.stevescarts.impl.block.CartAssemblerBlock;
import vswe.stevescarts.impl.block.JunctionRailBlock;
import vswe.stevescarts.impl.block.UpgradeBlock;

import java.util.Locale;

public enum StevesCartsBlocks implements ItemConvertible {
    CART_ASSEMBLER(new CartAssemblerBlock()),
    CARGO_MANAGER(createBlock()),
    LIQUID_MANAGER(createBlock()),

    EXTERNAL_DISTRIBUTOR(createBlock()),
    MODULE_TOGGLER(createBlock()),
    DETECTOR_UNIT(createBlock()),
    DETECTOR_MANAGER(createBlock()),
    DETECTOR_STATION(createBlock()),
    DETECTOR_JUNCTION(createBlock()),
    DETECTOR_REDSTONE_UNIT(createBlock()),

    REINFORCED_METAL_BLOCK(createBlock()),
    GALGADORIAN_BLOCK(createBlock()),
    ENHANCED_GALGADORIAN_BLOCK(createBlock()),

    ADVANCED_DETECTOR_RAIL(new DetectorRailBlock(FabricBlockSettings.of(Material.SUPPORTED).noCollision().strength(0.7f).sounds(BlockSoundGroup.METAL))),
    JUNCTION_RAIL(new JunctionRailBlock()),

    BATTERIES_UPGRADE(new UpgradeBlock()),
    POWER_CRYSTAL_UPGRADE(new UpgradeBlock()),
    MODULE_KNOWLEDGE_UPGRADE(new UpgradeBlock()),
    INDUSTRIAL_ESPIONAGE_UPGRADE(new UpgradeBlock()),
    EXPERIENCED_ASSEMBLER_UPGRADE(new UpgradeBlock()),
    NEW_ERA_UPGRADE(new UpgradeBlock()),
    CO2_FRIENDLY_UPGRADE(new UpgradeBlock()),
    GENERIC_ENGINE_UPGRADE(new UpgradeBlock()),
    MODULE_INPUT_UPGRADE(new UpgradeBlock()),
    PRODUCTION_LINE_UPGRADE(new UpgradeBlock()),

    CART_DEPLOYER_UPGRADE(new UpgradeBlock()),
    CART_MODIFIER_UPGRADE(new UpgradeBlock()),
    CART_CRANE_UPGRADE(new UpgradeBlock()),
    REDSTONE_CONTROL_UPGRADE(new UpgradeBlock()),
    CREATIVE_MODE_UPGRADE(new UpgradeBlock()),



    ;

    public final String name;
    public final Block block;

    StevesCartsBlocks(Block block){
        name = this.toString().toLowerCase(Locale.ROOT);
        this.block = block;
    }

    private static Block createBlock(){
        return new Block(FabricBlockSettings.of(Material.METAL).strength(2f, 2f).sounds(BlockSoundGroup.METAL));
    }

    @Override
    public Item asItem() {
        return block.asItem();
    }
}
