package vswe.stevescarts;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Validate;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.api.StevesCartsAPI;
import vswe.stevescarts.api.StevesCartsInitializer;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.component.ComponentSettings;
import vswe.stevescarts.impl.ComponentSettingsImpl;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class StevesCartsManager implements StevesCartsAPI {

	private static HashMap<Identifier, ComponentData<?>> components = new HashMap<>();

	public void load() {
		final StevesCartsAPI api = this;
		FabricLoader.getInstance().getEntrypoints("stevescarts", StevesCartsInitializer.class)
				.forEach(init -> init.onInitialize(api));
	}

	@Override
	public <T extends Component> void addComponent(Consumer<ComponentSettings<T>> consumer, Function<Component.InitData, T> functionFactory) {
		ComponentSettingsImpl<T> settings = new ComponentSettingsImpl<>();
		consumer.accept(settings);
		Validate.notNull(settings.getId(), "No id set");

		Validate.isTrue(!components.containsKey(settings.getId()), "A component with this name is already registered");
		components.put(settings.getId(), new ComponentData<>(settings, functionFactory));
	}

	public static ComponentData<?> getComponent(Identifier identifier) {
		return components.get(identifier);
	}

	public static class ComponentData<T extends Component> {
		final ComponentSettings<T> settings;
		final Function<Component.InitData, T> functionFactory;

		private ComponentData(ComponentSettings<T> settings, Function<Component.InitData, T> functionFactory) {
			this.settings = settings;
			this.functionFactory = functionFactory;
		}

		public ComponentSettings<T> getSettings() {
			return settings;
		}

		public Function<Component.InitData, T> getComponentFactory() {
			return functionFactory;
		}

		public T create(StevesCart cart) {
			//noinspection unchecked
			return getComponentFactory().apply(Component.InitData.create(cart, (ComponentSettings<Component>) getSettings()));
		}
	}
}
