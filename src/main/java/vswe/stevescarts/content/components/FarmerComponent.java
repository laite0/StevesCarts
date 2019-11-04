package vswe.stevescarts.content.components;

import vswe.stevescarts.api.component.Component;

public class FarmerComponent extends Component {

	private float farmAngle;
	private float rigAngle = -3.926991f;

	public FarmerComponent(InitData data) {
		super(data);
	}

	public void tick() {
		if(getCart().world.isClient){
			final float up = -3.926991f;
			final float down = -3.1415927f;
			final boolean flag = true;
			if (flag) {
				if (rigAngle < down) {
					rigAngle += 0.1f;
					if (rigAngle > down) {
						rigAngle = down;
					}
				} else {
					farmAngle = (float) ((farmAngle + 0.15f) % 6.283185307179586);
				}
			} else if (rigAngle > up) {
				rigAngle -= 0.075f;
				if (rigAngle < up) {
					rigAngle = up;
				}
			}
		}
	}

	public float getFarmAngle() {
		return farmAngle;
	}

	public void setFarmAngle(float farmAngle) {
		this.farmAngle = farmAngle;
	}

	public float getRigAngle() {
		return rigAngle;
	}

	public void setRigAngle(float rigAngle) {
		this.rigAngle = rigAngle;
	}
}
