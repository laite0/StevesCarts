package vswe.stevescarts.impl.block;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class UpgradeBlock extends Block {
    public static final DirectionProperty FACING;
    private static final Map<Direction, VoxelShape> FACING_TO_SHAPE;

    public UpgradeBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(2f, 2f).sounds(BlockSoundGroup.METAL));
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }


    // Block
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        for (Direction facing : context.getPlacementDirections()) {
            BlockState state = this.getDefaultState().with(FACING, facing.getOpposite());
            if (state.canPlaceAt(context.getWorld(), context.getBlockPos())) {
                return state;
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FACING_TO_SHAPE.get(state.get(FACING));
    }

    static {
        FACING = Properties.FACING;
        FACING_TO_SHAPE = Maps.newEnumMap(
                new ImmutableMap.Builder<Direction, VoxelShape>()
                        .put(Direction.NORTH, Block.createCuboidShape(3.0D, 3.0D, 14.0D, 13.0D, 13.0D, 16.0D))
                        .put(Direction.EAST, Block.createCuboidShape(0.0D, 3.0D, 3.0D, 2.0D, 13.0D, 13.0D))
                        .put(Direction.SOUTH, Block.createCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 2.0D))
                        .put(Direction.WEST, Block.createCuboidShape(14.0D, 3.0D, 3.0D, 16.0D, 13D, 13.0D))
                        .put(Direction.UP, Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D))
                        .put(Direction.DOWN, Block.createCuboidShape(3.0D, 14.0D, 3.0D, 13.0D, 16.0D, 13.0D))
                        .build());
    }
}
