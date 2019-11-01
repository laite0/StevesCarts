package vswe.stevescarts.impl;

import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Validate;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.component.ComponentSettings;
import vswe.stevescarts.api.component.ComponentType;
import vswe.stevescarts.impl.listeners.ListenerHandlerImpl;

public class ComponentSettingsImpl<T extends Component> extends ListenerHandlerImpl<T> implements ComponentSettings<T> {

	private Identifier identifier;

	@Override
	public void setId(Identifier identifier) {
		Validate.notNull(identifier, "identifier has already been set");
		this.identifier = identifier;
	}

	@Override
	public void setType(ComponentType type) {

	}

	public Identifier getId() {
		return identifier;
	}
}
