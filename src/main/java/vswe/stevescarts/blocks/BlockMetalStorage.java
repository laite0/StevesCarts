package vswe.stevescarts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.items.ItemBlockStorage;

public class BlockMetalStorage extends Block implements ModBlocks.ICustomItemBlock, ModBlocks.ISubtypeItemBlockModelDefinition {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, ItemBlockStorage.blocks.length - 1);

	public BlockMetalStorage() {
		super(Material.IRON);
		setCreativeTab(StevesCarts.tabsSC2Blocks);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, 0));
		setHardness(5.0F);
		setResistance(10.0F);
	}

	public int damageDropped(final int meta) {
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(TYPE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockStorage(this);
	}

	@Override
	public int getSubtypeNumber() {
		return ItemBlockStorage.blocks.length;
	}

	@Override
	public String getSubtypeName(int meta) {
		return "block_storage_" + ItemBlockStorage.blocks[meta];
	}
}
