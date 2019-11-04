package vswe.stevescarts.content.components;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.SystemUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.impl.util.InventoryUtils;

import java.util.Map;

public class LawnMowerComponent extends Component {

	private static final Map<DyeColor, ItemConvertible> DROPS = SystemUtil.consume(Maps.newEnumMap(DyeColor.class), (map) -> {
		map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
		map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
		map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
		map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
		map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
		map.put(DyeColor.LIME, Blocks.LIME_WOOL);
		map.put(DyeColor.PINK, Blocks.PINK_WOOL);
		map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
		map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
		map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
		map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
		map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
		map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
		map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
		map.put(DyeColor.RED, Blocks.RED_WOOL);
		map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
	});

	private float bladeangle;
	private float bladespeed;

	public LawnMowerComponent(InitData data) {
		super(data);
	}

	public void tick() {
		if (getCart().getEntityWorld().isClient) {
			bladeangle += getBladeSpindSpeed();
			if (true) {
				bladespeed = Math.min(0.2f, bladespeed + 0.002f);
			} else {
				bladespeed = Math.max(0.0f, bladespeed - 0.005f);
			}
			return;
		}

		if(getCart().canDoNewWork()) {
			shearSheep();
		}

		if(getCart().canDoNewWork()) {
			cutGrass();
		}
	}

	protected int radius() {
		return 7;
	}

	private void shearSheep() {
		if(getCart().world.isClient) {
			return;
		}
		getCart().world.getEntities(SheepEntity.class, getCart().getBoundingBox().expand(radius(), 2, radius())).stream()
				.filter(sheepEntity -> !sheepEntity.isSheared())
				.findFirst().ifPresent(this::shear);
	}

	private void cutGrass() {
		BlockPos.Mutable blockPos = new BlockPos.Mutable();
		final BlockPos cartPos = getCart().getBlockPos();
		for (int x = -radius(); x < radius(); x++) {
			for (int z = -radius(); z < radius(); z++) {
				for (int y = -2; y < 2; y++) {
					blockPos.set(cartPos.getX() + x, cartPos.getY() + y, cartPos.getZ() + z);
					BlockState state = getCart().world.getBlockState(blockPos);
					if(isCuttable(state)) {

						ServerWorld world = (ServerWorld) getCart().world;
						LootContext.Builder builder = new LootContext.Builder(world)
								.setRandom(world.random)
								.put(LootContextParameters.POSITION, blockPos)
								.put(LootContextParameters.TOOL, ItemStack.EMPTY);

						state.getDroppedStacks(builder).forEach(stack -> InventoryUtils.insertItem(stack, getCart()));

						getCart().world.setBlockState(blockPos, Blocks.AIR.getDefaultState());

						getCart().workingTime += 10;
						return;
					}
				}
			}
		}
	}

	protected boolean isCuttable(BlockState state) {
		return state.getMaterial() == Material.REPLACEABLE_PLANT || state.getMaterial() == Material.PLANT;
	}

	private void shear(SheepEntity sheepEntity) {
		if (sheepEntity.world.isClient || sheepEntity.isSheared()) {
			return;
		}
		sheepEntity.setSheared(true);

		int dropAmount = 1 + sheepEntity.getRand().nextInt(3);
		ItemStack stack = new ItemStack(DROPS.get(sheepEntity.getColor()), dropAmount);

		stack = InventoryUtils.insertItem(stack, getCart());

		if(!stack.isEmpty()) {
			//TODO drop on the floor
		}

		sheepEntity.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);

		getCart().workingTime += 20;
	}

	public float getBladeAngle() {
		return bladeangle;
	}

	public float getBladeSpindSpeed() {
		return bladespeed;
	}
}
