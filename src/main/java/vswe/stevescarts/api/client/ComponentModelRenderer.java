package vswe.stevescarts.api.client;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.model.Model;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.impl.client.CartEntityRenderer;
import vswe.stevescarts.impl.entity.CartEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentModelRenderer<C extends Component> extends Model implements ComponentRenderer<C> {

	private final List<Cuboid> cuboids = new ArrayList<>();

	@Override
	public void render(C component, CartEntity cart, double x, double y, double z, float deltaTicks, CartEntityRenderer renderer) {
		manipulateModel(component, cart, deltaTicks);
		cuboids.forEach(cuboid -> cuboid.render(0.0625F));
	}

	protected Cuboid createRenderCube(int u, int v) {
		Cuboid cuboid = createCube(u, v);
		cuboids.add(cuboid);
		return cuboid;
	}

	protected Cuboid createCube(int u, int v) {
		return new Cuboid(this, u, v);
	}

	protected void manipulateModel(C component, CartEntity cart, float partialtime) {

	}

}
