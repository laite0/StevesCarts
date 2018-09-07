package vswe.stevescarts.modules.realtimers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.modules.ModuleBase;

import javax.annotation.Nonnull;
import java.util.List;

public class ModuleCleaner extends ModuleBase {
	public ModuleCleaner(final EntityMinecartModular cart) {
		super(cart);
	}

	@Override
	public void update() {
		super.update();
		if (getCart().world.isRemote) {
			return;
		}
		if (getCart().hasFuel()) {
			suck();
		}
		clean();
	}

	private double calculatemotion(final double dif) {
		if (dif > -0.5D && dif < 0.5D) {
			return 0;
		}

		return 1 / (dif * 2);
	}

	private void suck() {
		List<Entity> list = getCart().world.getEntitiesWithinAABBExcludingEntity(getCart(), getCart().getEntityBoundingBox().grow(3.0, 1.0, 3.0));
		for (Entity e : list) {
			if (e instanceof EntityItem) {
				EntityItem eItem = (EntityItem) e;
				if (!eItem.cannotPickup()) {
					double difX = getCart().posX - eItem.posX;
					double difY = getCart().posY - eItem.posY;
					double difZ = getCart().posZ - eItem.posZ;
					eItem.motionX += calculatemotion(difX);
					eItem.motionY += calculatemotion(difY);
					eItem.motionZ += calculatemotion(difZ);
				}
			}
		}
	}

	private void clean() {
		final List<Entity> list = getCart().world.getEntitiesWithinAABBExcludingEntity(getCart(), getCart().getEntityBoundingBox().grow(1.0, 0.5, 1.0));
		for (Entity entity : list) {
			if (entity instanceof EntityItem) {
				EntityItem eItem = (EntityItem) entity;
				if (!eItem.cannotPickup() && !eItem.isDead) {
					int stackSize = eItem.getItem().getCount();
					getCart().addItemToChest(eItem.getItem());
					if (stackSize != eItem.getItem().getCount()) {
						getCart().world.playSound(null, getCart().getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 0.2f, ((this.getCart().random.nextFloat() - this.getCart().random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
						if (eItem.getItem().getCount() <= 0) {
							eItem.setDead();
						}
					} else if (failPickup(eItem.getItem())) {
						eItem.setDead();
					}
				}
			} else if (entity instanceof EntityArrow) {
				EntityArrow eItem2 = (EntityArrow) entity;
				if (Math.pow(eItem2.motionX, 2.0) + Math.pow(eItem2.motionY, 2.0) + Math.pow(eItem2.motionZ, 2.0) < 0.2 && eItem2.arrowShake <= 0 && !eItem2.isDead) {
					eItem2.arrowShake = 3;
					ItemStack iItem = new ItemStack(Items.ARROW, 1);
					getCart().addItemToChest(iItem);
					if (iItem.getCount() <= 0) {
						getCart().world.playSound(null, getCart().getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 0.2f, ((this.getCart().random.nextFloat() - this.getCart().random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
						eItem2.setDead();
					} else if (failPickup(iItem)) {
						eItem2.setDead();
					}
				}
			}
		}
	}

	private boolean failPickup(@Nonnull ItemStack item) {
		int x = normalize(getCart().pushZ);
		int z = normalize(getCart().pushX);
		if (x == 0 && z == 0) {
			return false;
		}

		EntityItem entityitem = new EntityItem(getCart().world, getCart().posX, getCart().posY, getCart().posZ, item.copy());
		entityitem.setPickupDelay(35);
		entityitem.motionX = x / 3.0f;
		entityitem.motionY = 0.15000000596046448;
		entityitem.motionZ = z / 3.0f;
		getCart().world.spawnEntity(entityitem);
		return true;
	}

	private int normalize(double val) {
		return Double.compare(val, 0.0);
	}
}
