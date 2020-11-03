package vswe.stevescarts.impl.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.RailBlock;
import net.minecraft.sound.BlockSoundGroup;

public class JunctionRailBlock extends RailBlock {
    public JunctionRailBlock() {
        super(FabricBlockSettings.of(Material.SUPPORTED).noCollision().strength(0.7f).sounds(BlockSoundGroup.METAL));
    }
}
