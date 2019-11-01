package vswe.stevescarts.impl.item;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vswe.stevescarts.impl.entity.CartEntity;

public class ItemCart extends Item {
	public ItemCart() {
		super(new Settings().group(ItemGroup.TRANSPORTATION));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		if (!blockState.matches(BlockTags.RAILS)) {
			return ActionResult.FAIL;
		} else {
			RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? blockState.get(((AbstractRailBlock) blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
			double double_1 = 0.0D;
			if (railShape.isAscending()) {
				double_1 = 0.5D;
			}

			if (!world.isClient) {
				CartEntity entity = new CartEntity(world, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.0625D + double_1, (double) blockPos.getZ() + 0.5D);
				world.spawnEntity(entity);
			}
			return ActionResult.SUCCESS;
		}
	}
}
