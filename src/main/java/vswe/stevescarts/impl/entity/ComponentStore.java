package vswe.stevescarts.impl.entity;

import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.StevesCartsManager;
import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.util.TriConsumer;
import vswe.stevescarts.impl.ComponentSettingsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ComponentStore {

	private List<Component> components = new ArrayList<>();
	private final CartEntity cart;

	public ComponentStore(CartEntity cart) {
		this.cart = cart;
		components.add(StevesCartsManager.getComponent(new Identifier(StevesCarts.MOD_ID, "solar_engine")).create(cart));
		components.add(StevesCartsManager.getComponent(new Identifier(StevesCarts.MOD_ID, "lawn_mower")).create(cart));


	}

	public void forEach(BiConsumer<Component, ComponentSettingsImpl<Component>> biConsumer) {
		components.forEach(component -> biConsumer.accept(component, (ComponentSettingsImpl<Component>) component.getSettings()));
	}

	public void forEach(Consumer<Component> componentConsumer) {
		components.forEach(componentConsumer);
	}

	public int size() {
		return components.size();
	}

	public Component getById(Identifier identifier) {
		return components.stream().filter(component -> component.getSettings().getId().equals(identifier)).findFirst().orElse(null);
	}

	<L extends Runnable> void fire(Class<L> listenerClass) {
		forEach((component, settings) -> settings.fire(component, listenerClass));
	}

	<A, L extends Consumer<A>> void fire(Class<L> listenerClass, A a) {
		forEach((component, settings) -> settings.fire(component, listenerClass, a));
	}

	<A, B, L extends BiConsumer<A, B>> void fire(Class<L> listenerClass, A a, B b) {
		forEach((component, settings) -> settings.fire(component, listenerClass, a, b));
	}

	<A, B, C, L extends TriConsumer<A, B, C>> void fire(Class<L> listenerClass, A a, B b, C c) {
		forEach((component, settings) -> settings.fire(component, listenerClass, a, b, c));
	}
}
