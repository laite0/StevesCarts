package vswe.stevescarts.api.client;

import net.minecraft.util.Identifier;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.impl.client.CartEntityRenderer;
import vswe.stevescarts.impl.entity.CartEntity;

public interface ComponentRenderer<C extends Component> {

	void renderComponent(C component, CartEntity cart, double x, double y, double z, float deltaTicks, CartEntityRenderer renderer);

	Identifier textureLocation(C component);

}
