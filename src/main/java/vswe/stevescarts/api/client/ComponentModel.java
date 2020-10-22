package vswe.stevescarts.api.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.impl.client.CartEntityRenderer;
import vswe.stevescarts.impl.entity.CartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ComponentModel<C extends Component> extends Model implements ComponentRenderer<C>
		{

	private final List<ModelPart> parts = new ArrayList<>();

	public ComponentModel(Function<Identifier, RenderLayer> layerFactory) {
		super(layerFactory);
	}

	public ComponentModel(){
		this(RenderLayer::getEntityCutoutNoCull);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		//manipulateModel(component, cart, deltaTicks);
		parts.forEach((modelPart) -> {
			modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		});
	}


	public void renderComponent(C component, CartEntity cart, double x, double y, double z, float deltaTicks, CartEntityRenderer renderer) {
		manipulateModel(component, cart, deltaTicks);
		//parts.forEach(part -> part.render(0.0625F * extraMult()));
	}

	public float extraMult() {
		return 1.0f;
	}

	protected ModelPart createRenderCube(int u, int v) {
		ModelPart part = createPart(u, v);
		parts.add(part);
		return part;
	}

	protected ModelPart createPart(int u, int v) {
		return new ModelPart(this, u, v);
	}

	protected void manipulateModel(C component, CartEntity cart, float partialtime) {

	}

}
