package vswe.stevescarts.blocks;

import net.minecraft.block.BlockRail;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.tracks.TrackManager;

import java.util.Map;

public class BlockModularTack extends BlockRail {

    public BlockModularTack() {
        super();
	    setCreativeTab(StevesCarts.tabsSC2Blocks);
        this.setDefaultState(createDefaultSate());
    }

	@Override
	protected BlockStateContainer createBlockState() {
		IProperty[] properties = TrackManager.propertyList.values().toArray(new IProperty[TrackManager.propertyList.values().size() + 1]);
		properties[TrackManager.propertyList.values().size()] = SHAPE;
		return new BlockStateContainer(this, properties);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
//		for(Map.Entry<IProperty<?>, String> mapEntry : TrackManager.defaultSateMap.entrySet()){
//			state = state.withProperty((IProperty)mapEntry.getKey(), mapEntry.getValue());
//		}
		return state;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta);
	}

	public IBlockState createDefaultSate() {
		IBlockState state = super.getDefaultState();
		for(Map.Entry<IProperty<?>, String> mapEntry : TrackManager.defaultSateMap.entrySet()){
			state = state.withProperty((IProperty)mapEntry.getKey(), mapEntry.getValue());
		}
		return state;
	}

	public static AxisAlignedBB getFlatABB(){
    	return FLAT_AABB;
	}
}
