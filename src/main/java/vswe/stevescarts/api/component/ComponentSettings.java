package vswe.stevescarts.api.component;

import net.minecraft.util.Identifier;
import vswe.stevescarts.api.client.ComponentRenderer;
import vswe.stevescarts.api.listeners.ListenerRegister;

public interface ComponentSettings<T extends Component> extends ListenerRegister<T> {

	void setId(Identifier identifier);
	void setType(ComponentType type);
	void renderer(ComponentRenderer<T> rendererSupplier);

	Identifier getId();
}
