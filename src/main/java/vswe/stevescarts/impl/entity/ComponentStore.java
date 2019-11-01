package vswe.stevescarts.impl.entity;

import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.StevesCartsManager;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.util.TriConsumer;
import vswe.stevescarts.impl.ComponentSettingsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ComponentStore {

	private List<Component> components = new ArrayList<>();
	private final StevesCart cart;

	public ComponentStore(StevesCart cart) {
		this.cart = cart;
		components.add(StevesCartsManager.getComponent(new Identifier(StevesCarts.MOD_ID, "creative_engine")).create(cart));
	}

	private void forEach(BiConsumer<Component, ComponentSettingsImpl<Component>> biConsumer) {
		components.forEach(component -> biConsumer.accept(component, (ComponentSettingsImpl<Component>) component.getSettings()));
	}

	<L extends Runnable> void listen(Class<L> listenerClass) {
		forEach((component, settings) -> settings.listen(component, listenerClass));
	}

	<A, L extends Consumer<A>> void listen(Class<L> listenerClass, A a) {
		forEach((component, settings) -> settings.listen(component, listenerClass, a));
	}

	<A, B, L extends BiConsumer<A, B>> void listen(Class<L> listenerClass, A a, B b) {
		forEach((component, settings) -> settings.listen(component, listenerClass, a, b));
	}

	<A, B, C, L extends TriConsumer<A, B, C>> void listen(Class<L> listenerClass, A a, B b, C c) {
		forEach((component, settings) -> settings.listen(component, listenerClass, a, b, c));
	}
}
