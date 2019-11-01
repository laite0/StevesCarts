package vswe.stevescarts.content.components;

import vswe.stevescarts.api.component.Component;

public class LawnMowerComponent extends Component {

	private float bladeangle;
	private float bladespeed;

	public LawnMowerComponent(InitData data) {
		super(data);
	}

	public void tick() {
		if (getCart().getWorld().isClient) {
			bladeangle += getBladeSpindSpeed();
			if (true) {
				bladespeed = Math.min(0.5f, bladespeed + 0.002f);
			} else {
				bladespeed = Math.max(0.0f, bladespeed - 0.005f);
			}
			return;
		}
	}

	public float getBladeAngle() {
		return bladeangle;
	}

	public float getBladeSpindSpeed() {
		return bladespeed;
	}
}
