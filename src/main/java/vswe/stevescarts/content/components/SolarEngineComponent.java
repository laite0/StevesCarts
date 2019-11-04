package vswe.stevescarts.content.components;

import net.minecraft.entity.player.PlayerEntity;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.network.Synced;

public class SolarEngineComponent extends Component {

	@Synced
	private boolean down;

	private float minVal;
	private float maxVal;
	private float minAngle;
	private float maxAngle;
	private float innerRotation;
	private float movingLevel;

	public SolarEngineComponent(InitData data) {
		super(data);
		minVal = -4.0f;
		maxVal = -13.0f;
		minAngle = 0.0f;
		maxAngle = 1.5707964f;
		innerRotation = 0.0f;
		movingLevel = minVal;
	}

	public float getInnerRotation() {
		return innerRotation;
	}

	public float getMovingLevel() {
		return movingLevel;
	}

	public boolean updatePanels() {
		if (movingLevel > minVal) {
			movingLevel = minVal;
		}
		if (innerRotation < minAngle) {
			innerRotation = minAngle;
		} else if (innerRotation > maxAngle) {
			innerRotation = maxAngle;
		}
		final float targetAngle = isGoingDown() ? minAngle : maxAngle;
		if (movingLevel > maxVal && innerRotation != targetAngle) {
			movingLevel -= 0.2f;
			if (movingLevel <= maxVal) {
				movingLevel = maxVal;
			}
		} else if (innerRotation != targetAngle) {
			innerRotation += (isGoingDown() ? -0.05f : 0.05f);
			if ((!isGoingDown() && innerRotation >= targetAngle) || (isGoingDown() && innerRotation <= targetAngle)) {
				innerRotation = targetAngle;
			}
		} else if (movingLevel < minVal) {
			movingLevel += 0.2f;
			if (movingLevel >= minVal) {
				movingLevel = minVal;
			}
		}
		return innerRotation == maxAngle;
	}

	boolean isGoingDown() {
		return down;
	}

	public void tick() {
		updatePanels();
	}

	public void use(PlayerEntity playerEntity) {
		if(!getCart().getWorld().isClient) {
			down = !down;
		}
	}

}
