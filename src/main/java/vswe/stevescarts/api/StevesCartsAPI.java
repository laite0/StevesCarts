package vswe.stevescarts.api;


import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.component.ComponentSettings;

import java.util.function.Consumer;
import java.util.function.Function;

public interface StevesCartsAPI {

	<T extends Component> void addComponent(Consumer<ComponentSettings<T>> settingsConsumer, Function<Component.InitData, T> functionFactory);

}
