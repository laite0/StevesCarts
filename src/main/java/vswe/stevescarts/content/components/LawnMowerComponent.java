package vswe.stevescarts.content.components;

import net.minecraft.entity.passive.SheepEntity;
import vswe.stevescarts.api.component.Component;

public class LawnMowerComponent extends Component {

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
		shearSheep();
	}

	public void shearSheep() {
		getCart().world.getEntities(SheepEntity.class, getCart().getBoundingBox().expand(7, 2, 7)).stream()
				.filter(sheepEntity -> !sheepEntity.isSheared())
				.forEach(SheepEntity::dropItems); //TODO put the items in the inv of the cart
	}

	public float getBladeAngle() {
		return bladeangle;
	}

	public float getBladeSpindSpeed() {
		return bladespeed;
	}
}
