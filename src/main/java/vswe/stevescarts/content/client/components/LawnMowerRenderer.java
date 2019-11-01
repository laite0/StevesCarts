package vswe.stevescarts.content.client.components;

import net.minecraft.client.model.Cuboid;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.api.client.ComponentModelRenderer;
import vswe.stevescarts.content.components.LawnMowerComponent;

import java.util.ArrayList;

public class LawnMowerRenderer extends ComponentModelRenderer<LawnMowerComponent> {

	private ArrayList<Cuboid> bladepins = new ArrayList<>();

	public LawnMowerRenderer() {
		createSide(false);
		createSide(true);
	}

	@Override
	protected void manipulateModel(LawnMowerComponent component, StevesCart cart, float deltaTicks) {
		final float angle = component.getBladeAngle() + deltaTicks * component.getBladeSpindSpeed();

		for (int i = 0; i < bladepins.size(); ++i) {
			final Cuboid bladepin = bladepins.get(i);
			if (i % 2 == 0) {
				bladepin.pitch = angle;
			} else {
				bladepin.pitch = -angle;
			}
		}
	}

	private void createSide(final boolean opposite) {
		final Cuboid anchor = createRenderCube(0, 0);

		if (opposite) {
			anchor.yaw = 3.1415927f;
		}

		final Cuboid base = new Cuboid(this, 0, 0);
		anchor.addChild(base);

		base.addBox(-11.5f, -3.0f, -1.0f, 23, 6, 2, 0.0f);
		base.setRotationPoint(0.0f, -1.5f, -9.0f);
		for (int i = 0; i < 2; ++i) {
			final Cuboid arm = new Cuboid(this, 0, 8);
			base.addChild(arm);
			arm.addBox(-8.0f, -1.5f, -1.5f, 16, 3, 3, 0.0f);
			arm.setRotationPoint(-8.25f + i * 16.5f, 0.0f, -8.0f);
			arm.yaw = 1.5707964f;

			final Cuboid arm2 = new Cuboid(this, 0, 14);
			arm.addChild(arm2);
			arm2.addBox(-1.5f, -1.5f, -1.5f, 3, 3, 3, 0.0f);
			arm2.setRotationPoint(6.5f, 3.0f, 0.0f);
			arm2.roll = 1.5707964f;

			final Cuboid bladepin = new Cuboid(this, 0, 20);
			arm2.addChild(bladepin);
			bladepin.addBox(-1.0f, -0.5f, -0.5f, 2, 1, 1, 0.0f);
			bladepin.setRotationPoint(2.5f, 0.0f, 0.0f);

			final Cuboid bladeanchor = new Cuboid(this, 0, 0);
			bladepin.addChild(bladeanchor);
			bladeanchor.yaw = 1.5707964f;

			for (int j = 0; j < 4; ++j) {
				final Cuboid blade = new Cuboid(this, 0, 22);
				bladeanchor.addChild(blade);
				blade.addBox(-1.5f, -1.5f, -0.5f, 8, 3, 1, 0.0f);
				blade.setRotationPoint(0.0f, 0.0f, j * 0.01f);
				blade.roll = 1.5707964f * (j + i * 0.5f);

				final Cuboid bladetip = new Cuboid(this, 0, 26);
				blade.addChild(bladetip);
				bladetip.addBox(6.5f, -1.0f, -0.5f, 6, 2, 1, 0.0f);
				bladetip.setRotationPoint(0.0f, 0.0f, 0.005f);
			}

			bladepins.add(bladepin);
		}
	}

	@Override
	public Identifier textureLocation(LawnMowerComponent component) {
		return new Identifier(StevesCarts.MOD_ID, "textures/models/lawnmowermodel.png");
	}
}
