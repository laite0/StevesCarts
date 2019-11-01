package vswe.stevescarts.api.component;

import vswe.stevescarts.api.StevesCart;

public abstract class Component {

	private final StevesCart cart;
	private final ComponentSettings<Component> settings;

	public Component(InitData data) {
		this.cart = data.cart;
		this.settings = data.componentSettings;
	}

	public StevesCart getCart() {
		return cart;
	}

	public ComponentSettings<Component> getSettings() {
		return settings;
	}

	public static class InitData {
		final StevesCart cart;
		final ComponentSettings<Component> componentSettings;

		private InitData(StevesCart cart, ComponentSettings<Component> componentSettings) {
			this.cart = cart;
			this.componentSettings = componentSettings;
		}

		public static InitData create(StevesCart cart, ComponentSettings<Component> componentSettings) {
			return new InitData(cart, componentSettings);
		}
	}
}
