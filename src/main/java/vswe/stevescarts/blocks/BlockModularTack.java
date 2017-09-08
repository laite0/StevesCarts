package vswe.stevescarts.blocks;

import net.minecraft.block.BlockRail;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.arcade.tracks.Track;
import vswe.stevescarts.blocks.tileentities.TileEntityModularTack;
import vswe.stevescarts.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BlockModularTack extends BlockRail implements ITileEntityProvider {

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
		TileEntityModularTack tileEntityModularTack = (TileEntityModularTack) getTileEntitySafely(worldIn, pos);
		return tileEntityModularTack.getExtraStates(state);
	}

	//see for more info https://www.reddit.com/r/feedthebeast/comments/5mxwq9/psa_mod_devs_do_you_call_worldgettileentity_from/
	public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return blockAccess.getTileEntity(pos);
		}
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

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityModularTack();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(TrackList.TrackModule slepper : TrackManager.getAllModulesForType(TrackList.TrackModuleType.SLEEPER)){
			for(TrackList.TrackModule rail : TrackManager.getAllModulesForType(TrackList.TrackModuleType.RAIL)){
				ItemStack stack = new ItemStack(this, 1);
				List<TrackList.TrackModule> moduleList = new ArrayList<>();
				moduleList.add(slepper);
				moduleList.add(rail);
				stack.setTagCompound(TrackManager.toNBT(moduleList));
				items.add(stack);
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack,
	                           @Nullable
		                           World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		List<TrackList.TrackModule> moduleList = TrackManager.fromNBT(stack.getTagCompound());
		for(TrackList.TrackModule module : moduleList){
			tooltip.add(module.type.name() + ":" + module.name);
		}
	}
}
