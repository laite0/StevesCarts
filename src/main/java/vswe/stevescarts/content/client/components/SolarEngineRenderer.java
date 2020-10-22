package vswe.stevescarts.content.client.components;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.client.ComponentModel;
import vswe.stevescarts.content.components.SolarEngineComponent;
import vswe.stevescarts.impl.entity.CartEntity;

import java.util.ArrayList;

public class SolarEngineRenderer extends ComponentModel<SolarEngineComponent> {

	private final ModelPart base;
	private final ArrayList<ModelPart> panels = new ArrayList<>();

	public SolarEngineRenderer() {
		textureHeight = 16;
		textureWidth = 32;

		base = createRenderCube(0, 0);
		base.addCuboid(-1.0f, -5.0f, -1.0f, 2, 10, 2, 0.0f);

		final ModelPart moving = createRenderCube(8, 0);
		moving.addCuboid(-2.0f, -3.5f, -2.0f, 4, 7, 4, 0.0f);

		final ModelPart top = createRenderCube(0, 12);
		moving.addChild(top);
		top.setTextureSize(64, 64);

		top.addCuboid(-6.0f, -1.5f, -2.0f, 12, 3, 4, 0.0f);
		top.setPivot(0.0f, -5.0f, 0.0f);

		for (int i = 0; i < 4; ++i) {
			createPanel(base, i);
		}
	}

	@Override
	protected void manipulateModel(SolarEngineComponent component, CartEntity cart, float deltaTicks) {
		panels.forEach(cuboid -> cuboid.pitch = -component.getInnerRotation());
		base.pivotY = component.getMovingLevel();
	}

//	@Override
	public Identifier textureLocation(SolarEngineComponent component) {
		if(component.isFullyDown()) {
			return new Identifier(StevesCarts.MOD_ID, "textures/models/panelmodelactive.png");
		}
		return new Identifier(StevesCarts.MOD_ID, "textures/models/panelmodelidle.png");
	}

	private ModelPart createPanel(final ModelPart base, final int index) {
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

		final ModelPart panel = createRenderCube(0, 0);
		base.addChild(panel);

		panel.addCuboid(-6.0f, 0.0f, -2.0f, 12, 13, 2, 0.0f);
		panel.setPivot((float) Math.sin(rotation) * f, -5.0f, (float) Math.cos(rotation) * f);
		panel.yaw = rotation;

		panels.add(panel);
		return panel;
	}
}
