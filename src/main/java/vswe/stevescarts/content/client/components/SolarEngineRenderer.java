package vswe.stevescarts.content.client.components;

import net.minecraft.client.model.Cuboid;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.api.client.ComponentModelRenderer;
import vswe.stevescarts.content.components.SolarEngineComponent;

import java.util.ArrayList;

public class SolarEngineRenderer extends ComponentModelRenderer<SolarEngineComponent> {

	private final Cuboid base;
	private final ArrayList<Cuboid> panels = new ArrayList<>();

	public SolarEngineRenderer() {
		textureHeight = 16;
		textureWidth = 32;

		base = createRenderCube(0, 0);
		base.addBox(-1.0f, -5.0f, -1.0f, 2, 10, 2, 0.0f);

		final Cuboid moving = createCube(8, 0);
		moving.addBox(-2.0f, -3.5f, -2.0f, 4, 7, 4, 0.0f);

		final Cuboid top = createCube(0, 12);
		moving.addChild(top);
		top.setTextureSize(64, 64);

		top.addBox(-6.0f, -1.5f, -2.0f, 12, 3, 4, 0.0f);
		top.setRotationPoint(0.0f, -5.0f, 0.0f);

		for (int i = 0; i < 4; ++i) {
			createPanel(base, i);
		}
	}

	@Override
	protected void manipulateModel(SolarEngineComponent component, StevesCart cart) {
		panels.forEach(cuboid -> cuboid.pitch = -component.getInnerRotation());
		base.rotationPointY = component.getMovingLevel();
	}

	@Override
	public Identifier textureLocation(SolarEngineComponent component) {
		return new Identifier(StevesCarts.MOD_ID, "textures/models/panelmodelactive.png");
	}

	private Cuboid createPanel(final Cuboid base, final int index) {
		float rotation = 0.0f;
		float f = 0.0f;
		switch (index) {
			case 0: {
				rotation = 0.0f;
				f = -1.5f;
				break;
			}
			case 1: {
				rotation = 3.1415927f;
				f = -1.5f;
				break;
			}
			case 2: {
				rotation = 4.712389f;
				f = -6.0f;
				break;
			}
			case 3: {
				rotation = 1.5707964f;
				f = -6.0f;
				break;
			}
		}

		final Cuboid panel = createCube(0, 0);
		base.addChild(panel);

		panel.addBox(-6.0f, 0.0f, -2.0f, 12, 13, 2, 0.0f);
		panel.setRotationPoint((float) Math.sin(rotation) * f, -5.0f, (float) Math.cos(rotation) * f);
		panel.yaw = rotation;

		panels.add(panel);
		return panel;
	}
}
