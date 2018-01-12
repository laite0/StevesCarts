package vswe.stevescarts.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.blocks.tileentities.TileEntityLiquid;

public class BlockLiquidManager extends BlockContainerBase {

	public BlockLiquidManager() {
		super(Material.ROCK);
		setCreativeTab(StevesCarts.tabsSC2Blocks);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		final TileEntityLiquid tile = (TileEntityLiquid) world.getTileEntity(pos);
		if (tile != null) {
			InventoryHelper.dropInventoryItems(world, pos, tile);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}
		player.openGui(StevesCarts.instance, 2, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int var2) {
		return new TileEntityLiquid();
	}
}
