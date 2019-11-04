package vswe.stevescarts.impl;

import net.minecraft.util.Identifier;
import vswe.stevescarts.api.client.ComponentRenderer;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.component.ComponentSettings;
import vswe.stevescarts.api.component.ComponentType;
import vswe.stevescarts.impl.listeners.ListenerHandlerImpl;

public class ComponentSettingsImpl<T extends Component> extends ListenerHandlerImpl<T> implements ComponentSettings<T> {

	private Identifier identifier;
	private ComponentRenderer<T> renderer = null;

	@Override
	public void setId(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void setType(ComponentType type) {

	}

	@Override
	public void renderer(ComponentRenderer<T> rendererSupplier) {
		renderer = rendererSupplier;
	}

	public ComponentRenderer<T> getRenderer() {
		return renderer;
	}

	@Override
	public Identifier getId() {
		return identifier;
	}
}
