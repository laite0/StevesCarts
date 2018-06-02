package vswe.stevescarts.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.blocks.tileentities.TileEntityDetector;
import vswe.stevescarts.helpers.DetectorType;
import vswe.stevescarts.items.ItemBlockDetector;

public class BlockDetector extends BlockContainerBase implements ModBlocks.ICustomItemBlock, ModBlocks.ISubtypeItemBlockModelDefinition, ModBlocks.IStateMappedBlock {

	public static PropertyEnum<DetectorType> SATE = PropertyEnum.create("detectortype", DetectorType.class);
	public static PropertyBool POWERED = PropertyBool.create("powered");

	public BlockDetector() {
		super(Material.CIRCUITS);
		setCreativeTab(StevesCarts.tabsSC2Blocks);
		setDefaultState(getStateFromMeta(0));
	}

	@Override
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list) {
		for (final DetectorType type : DetectorType.values()) {
			list.add(new ItemStack(this, 1, type.getMeta()));
		}
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityPlayer, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (entityPlayer.isSneaking()) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}
		FMLNetworkHandler.openGui(entityPlayer, StevesCarts.instance, 6, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) && DetectorType.getTypeFromSate(blockState).shouldEmitRedstone() ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side == null) {
			return false;
		}
		final DetectorType type = DetectorType.getTypeFromSate(state);
		return type.shouldEmitRedstone() || type == DetectorType.REDSTONE;
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int var2) {
		return new TileEntityDetector();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean powered = false;
		if (meta > DetectorType.values().length) {
			powered = true;
		}
		return getDefaultState().withProperty(SATE, DetectorType.getTypeFromint(meta - (powered ? DetectorType.values().length + 1 : 0))).withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		boolean powered = state.getValue(POWERED);
		return (state.getValue(SATE)).getMeta() + (powered ? DetectorType.values().length + 1 : 0);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SATE, POWERED);
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockDetector(this);
	}

	@Override
	public int getSubtypeNumber() {
		return DetectorType.values().length;
	}

	@Override
	public String getSubtypeName(int meta) {
		return "block_detector_" + DetectorType.values()[meta].getName();
	}

	@Override
	public void setStateMapper(StateMap.Builder builder) {
		builder.ignore(POWERED);
	}
}
