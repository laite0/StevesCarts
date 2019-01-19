package vswe.stevescarts.compat.techreborn;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import techreborn.api.TechRebornAPI;
import techreborn.blocks.BlockRubberLog;
import techreborn.init.ModBlocks;
import techreborn.init.ModSounds;
import vswe.stevescarts.api.farms.EnumHarvestResult;
import vswe.stevescarts.api.farms.ITreeProduceModule;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.modules.workers.tools.ModuleTreeTap;
import vswe.stevescarts.modules.workers.tools.ModuleWoodcutter;

public class TRRubberTreeModule implements ITreeProduceModule {

	@Override
	public EnumHarvestResult isLeaves(IBlockState blockState, BlockPos pos, EntityMinecartModular cart) {
		if (blockState.getBlock() == ModBlocks.RUBBER_LEAVES) {
			if (cart.hasModule(ModuleTreeTap.class)) {
				return EnumHarvestResult.DISALLOW;
			}
			return EnumHarvestResult.ALLOW;
		}
		return EnumHarvestResult.SKIP;
	}

	@Override
	public EnumHarvestResult isWood(IBlockState blockState, BlockPos pos, EntityMinecartModular cart) {
		if (blockState.getBlock() == ModBlocks.RUBBER_LOG) {
			return EnumHarvestResult.ALLOW;
		}
		return EnumHarvestResult.SKIP;
	}

	@Override
	public boolean isSapling(ItemStack itemStack) {
		return itemStack.getItem() == Item.getItemFromBlock(ModBlocks.RUBBER_SAPLING);
	}

	@Override
	public boolean plantSapling(World world, BlockPos pos, ItemStack stack, FakePlayer fakePlayer) {
		Block block = Block.getBlockFromItem(stack.getItem());
		if (block.canPlaceBlockAt(world, pos.up())) {
			world.setBlockState(pos.up(), block.getDefaultState());
			return true;
		}
		return false;
	}

	@Override
	public boolean harvest(IBlockState blockState, BlockPos pos, EntityMinecartModular cart, NonNullList<ItemStack> drops, boolean simulate, ModuleWoodcutter woodcutter) {
		if (!cart.hasModule(ModuleTreeTap.class)) {
			return false;
		}
		BlockPos workPos = pos;
		IBlockState workSate = cart.world.getBlockState(workPos);
		boolean foundBlock = false;
		while (isWood(workSate, workPos, cart) == EnumHarvestResult.ALLOW) {
			if (workSate.getBlock() instanceof BlockRubberLog) {
				foundBlock = true;
				if (workSate.getValue(BlockRubberLog.HAS_SAP)) {
					ItemStack sap = TechRebornAPI.subItemRetriever.getPartByName("rubberSap").copy();
					drops.add(sap);
					if (!simulate) {
						cart.world.setBlockState(workPos, workSate.withProperty(BlockRubberLog.HAS_SAP, false));
						cart.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.SAP_EXTRACT, SoundCategory.BLOCKS,
							0.6F, 1F);
						woodcutter.damageTool(1);
						woodcutter.startWorking(20);
					}
				}
				workPos = workPos.up();
				workSate = cart.world.getBlockState(workPos);
			}
		}
		return foundBlock;
	}
}
