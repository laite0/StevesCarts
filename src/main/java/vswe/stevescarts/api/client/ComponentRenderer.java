package vswe.stevescarts.api.client;

import net.minecraft.util.Identifier;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.impl.client.CartEntityRenderer;

public interface ComponentRenderer<C extends Component> {

	void render(C component, StevesCart cart, double x, double y, double z, float deltaTicks, CartEntityRenderer renderer);

	Identifier textureLocation(C component);

}
