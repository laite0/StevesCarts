package vswe.stevescarts.content.client.components;

import net.minecraft.client.model.Cuboid;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.client.ComponentModelRenderer;
import vswe.stevescarts.content.components.FarmerComponent;
import vswe.stevescarts.impl.entity.CartEntity;

public class FarmerRenderer extends ComponentModelRenderer<FarmerComponent> {

	private Cuboid mainAnchor;
	private Cuboid anchor;
	private Cuboid[] outers;

	public FarmerRenderer() {
		textureHeight = 64;
		textureWidth = 128;

		mainAnchor = createRenderCube(0, 0);
		
		mainAnchor.setRotationPoint(-18.0f, 4.0f, 0.0f);
		for (int i = -1; i <= 1; i += 2) {
			final Cuboid smallarm = new Cuboid(this, 26, 23);
			mainAnchor.addChild(smallarm);

			smallarm.addBox(-1.0f, -1.0f, -1.0f, 8, 2, 2, 0.0f);
			smallarm.setRotationPoint(0.0f, 0.0f, i * 17);
		}
		final Cuboid mainarm = new Cuboid(this, 0, 37);
		mainAnchor.addChild(mainarm);

		mainarm.addBox(-30.0f, -2.0f, -2.0f, 60, 4, 4, 0.0f);
		mainarm.setRotationPoint(8.0f, 0.0f, 0.0f);
		mainarm.yaw = 1.5707964f;
		for (int j = -1; j <= 1; j += 2) {
			final Cuboid extra = new Cuboid(this, 26, 27);
			mainAnchor.addChild(extra);

			extra.addBox(-2.5f, -2.5f, -1.0f, 5, 5, 2, 0.0f);
			extra.setRotationPoint(8.0f, 0.0f, j * 30);
			final Cuboid bigarm = new Cuboid(this, 26, 17);
			mainAnchor.addChild(bigarm);

			bigarm.addBox(-1.0f, -2.0f, -1.0f, 16, 4, 2, 0.0f);
			bigarm.setRotationPoint(8.0f, 0.0f, j * 32);
		}
		anchor = new Cuboid(this);
		mainAnchor.addChild(anchor);
		anchor.setRotationPoint(22.0f, 0.0f, 0.0f);
		final float start = -1.5f;
		final float end = 1.5f;
		for (float k = -1.5f; k <= 1.5f; ++k) {
			for (int l = 0; l < 6; ++l) {
				final Cuboid side = new Cuboid(this, 0, 0);
				anchor.addChild(side);

				side.addBox(-5.0f, -8.8f, -1.0f, 10, 4, 2, 0.0f);
				side.setRotationPoint(0.0f, 0.0f, k * 20.0f + l % 2 * 0.005f);
				side.roll = l * 6.2831855f / 6.0f;
			}
			if (k == start || k == end) {
				final Cuboid sidecenter = new Cuboid(this, 0, 12);
				anchor.addChild(sidecenter);

				sidecenter.addBox(-6.0f, -6.0f, -0.5f, 12, 12, 1, 0.0f);
				sidecenter.setRotationPoint(0.0f, 0.0f, k * 20.0f);
			} else {
				for (int l = 0; l < 3; ++l) {
					final Cuboid sidecenter2 = new Cuboid(this, 26, 12);
					anchor.addChild(sidecenter2);

					sidecenter2.addBox(-1.0f, -2.0f, -0.5f, 8, 4, 1, 0.0f);
					sidecenter2.setRotationPoint(0.0f, 0.0f, k * 20.0f);
					sidecenter2.roll = (l + 0.25f) * 6.2831855f / 3.0f;
				}
			}
		}
		for (int m = 0; m < 6; ++m) {
			final Cuboid middle = new Cuboid(this, 0, 6);
			anchor.addChild(middle);

			middle.addBox(-30.0f, -1.7f, -1.0f, 60, 2, 2, 0.0f);
			middle.setRotationPoint(0.0f, 0.0f, m % 2 * 0.005f);
			middle.pitch = m * 6.2831855f / 6.0f;
			middle.yaw = 1.5707964f;
		}
		outers = new Cuboid[6];
		for (int m = 0; m < 6; ++m) {
			final Cuboid nailAnchor = new Cuboid(this);
			anchor.addChild(nailAnchor);
			nailAnchor.pitch = nailRot(m);
			nailAnchor.yaw = 1.5707964f;
			final Cuboid outer = new Cuboid(this, 0, 10);
			nailAnchor.addChild(outer);

			outer.addBox(-30.0f, -0.5f, -0.5f, 60, 1, 1, 0.0f);
			outer.setRotationPoint(0.0f, -8.8f, 0.0f);
			outer.pitch = 3.1415927f;
			outers[m] = outer;
			for (int j2 = -13; j2 <= 13; ++j2) {
				if (Math.abs(j2) > 6 || Math.abs(j2) < 4) {
					final Cuboid nail = new Cuboid(this, 44, 13);
					outer.addChild(nail);

					nail.addBox(-0.5f, -1.5f, -0.5f, 1, 3, 1, 0.0f);
					nail.setRotationPoint(j2 * 2, -2.0f, 0.0f);
				}
			}
		}
	}

	@Override
	protected void manipulateModel(FarmerComponent component, CartEntity cart, float partialtime) {
		mainAnchor.roll = -component.getRigAngle();
		final float farmAngle = component.getFarmAngle();
		anchor.roll = -farmAngle;
		for (int i = 0; i < 6; ++i) {
			outers[i].pitch = farmAngle + nailRot(i);
		}
	}

	private float nailRot(final int i) {
		return (i + 0.5f) * 6.2831855f / 6.0f;
	}

	@Override
	public float extraMult() {
		return 0.5f;
	}

	@Override
	public Identifier textureLocation(FarmerComponent component) {
		return new Identifier(StevesCarts.MOD_ID, "textures/models/farmermodeldiamond.png");
	}
}
