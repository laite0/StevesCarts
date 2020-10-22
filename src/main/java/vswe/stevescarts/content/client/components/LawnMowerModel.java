package vswe.stevescarts.content.client.components;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.client.ComponentModel;
import vswe.stevescarts.content.components.LawnMowerComponent;
import vswe.stevescarts.impl.entity.CartEntity;

import java.util.ArrayList;

public class LawnMowerModel extends ComponentModel<LawnMowerComponent> {

	private ArrayList<ModelPart> bladepins = new ArrayList<>();

	public LawnMowerModel() {
		createSide(false);
		createSide(true);
	}

	@Override
	protected void manipulateModel(LawnMowerComponent component, CartEntity cart, float deltaTicks) {
		final float angle = component.getBladeAngle() + deltaTicks * component.getBladeSpindSpeed();

		for (int i = 0; i < bladepins.size(); ++i) {
			final ModelPart bladepin = bladepins.get(i);
			if (i % 2 == 0) {
				bladepin.pitch = angle;
			} else {
				bladepin.pitch = -angle;
			}
		}
	}

	private void createSide(final boolean opposite) {
		final ModelPart anchor = createRenderCube(0, 0);

		if (opposite) {
			anchor.yaw = 3.1415927f;
		}

		final ModelPart base = new ModelPart(this, 0, 0);
		anchor.addChild(base);

		base.addCuboid(-11.5f, -3.0f, -1.0f, 23, 6, 2, 0.0f);
		base.setPivot(0.0f, -1.5f, -9.0f);
		for (int i = 0; i < 2; ++i) {
			final ModelPart arm = new ModelPart(this, 0, 8);
			base.addChild(arm);
			arm.addCuboid(-8.0f, -1.5f, -1.5f, 16, 3, 3, 0.0f);
			arm.setPivot(-8.25f + i * 16.5f, 0.0f, -8.0f);
			arm.yaw = 1.5707964f;

			final ModelPart arm2 = new ModelPart(this, 0, 14);
			arm.addChild(arm2);
			arm2.addCuboid(-1.5f, -1.5f, -1.5f, 3, 3, 3, 0.0f);
			arm2.setPivot(6.5f, 3.0f, 0.0f);
			arm2.roll = 1.5707964f;

			final ModelPart bladepin = new ModelPart(this, 0, 20);
			arm2.addChild(bladepin);
			bladepin.addCuboid(-1.0f, -0.5f, -0.5f, 2, 1, 1, 0.0f);
			bladepin.setPivot(2.5f, 0.0f, 0.0f);

			final ModelPart bladeanchor = new ModelPart(this, 0, 0);
			bladepin.addChild(bladeanchor);
			bladeanchor.yaw = 1.5707964f;

			for (int j = 0; j < 4; ++j) {
				final ModelPart blade = new ModelPart(this, 0, 22);
				bladeanchor.addChild(blade);
				blade.addCuboid(-1.5f, -1.5f, -0.5f, 8, 3, 1, 0.0f);
				blade.setPivot(0.0f, 0.0f, j * 0.01f);
				blade.roll = 1.5707964f * (j + i * 0.5f);

				final ModelPart bladetip = new ModelPart(this, 0, 26);
				blade.addChild(bladetip);
				bladetip.addCuboid(6.5f, -1.0f, -0.5f, 6, 2, 1, 0.0f);
				bladetip.setPivot(0.0f, 0.0f, 0.005f);
			}

			bladepins.add(bladepin);
		}
	}

	//@Override
	public Identifier textureLocation(LawnMowerComponent component) {
		return new Identifier(StevesCarts.MOD_ID, "textures/models/lawnmowermodel.png");
	}
}
