package vswe.stevescarts.content.client.components;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.model.Model;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.api.client.ComponentRenderer;
import vswe.stevescarts.content.components.SolarEngineComponent;
import vswe.stevescarts.impl.client.CartEntityRenderer;

import java.util.ArrayList;

public class SolarEngineRenderer implements ComponentRenderer<SolarEngineComponent> {

	SolarModel model = new SolarModel();

	@Override
	public void render(SolarEngineComponent component, StevesCart cart, double x, double y, double z, float f, CartEntityRenderer renderer) {
		renderer.bindTexture(new Identifier(StevesCarts.MOD_ID, "textures/models/panelmodelactive.png"));
		model = new SolarModel();

		for (final Cuboid panel : model.panels) {
			panel.pitch = -component.getInnerRotation();
			panel.render(f);
		}

		model.base.rotationPointY = component.getMovingLevel();

		renderer.bindTexture(new Identifier("textures/block/iron_block.png"));
		model.base.render(f);
	}

	private static class SolarModel extends Model {

		final Cuboid base;
		final ArrayList<Cuboid> panels = new ArrayList<>();

		public SolarModel() {
			textureHeight = 16;
			textureWidth = 32;

			base = new Cuboid(this, 0, 0);
			base.addBox(-1.0f, -5.0f, -1.0f, 2, 10, 2, 0.0f);
			base.setRotationPoint(0.0f, -4.5f, 0.0f);

			final Cuboid moving = new Cuboid(this, 8, 0);
			moving.addBox(-2.0f, -3.5f, -2.0f, 4, 7, 4, 0.0f);


			final Cuboid top = new Cuboid(this, 0, 12);
			moving.addChild(top);
			top.setTextureSize(64, 64);

			top.addBox(-6.0f, -1.5f, -2.0f, 12, 3, 4, 0.0f);
			top.setRotationPoint(0.0f, -5.0f, 0.0f);

			for (int i = 0; i < 4; ++i) {
				createPanel(i);
			}
		}

		private Cuboid createPanel(final int index) {
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
			return createPanel(rotation, f);
		}


		private Cuboid createPanel(final float rotation, final float f) {
			final Cuboid panel = new Cuboid(this, 0, 0);
			//base.addChild(panel);
			panel.addBox(-6.0f, 0.0f, -2.0f, 12, 13, 2, 0.0f);
			panel.setRotationPoint((float) Math.sin(rotation) * f, -5.0f, (float) Math.cos(rotation) * f);
			panel.yaw = rotation;
			panels.add(panel);
			return panel;
		}
	}
}
