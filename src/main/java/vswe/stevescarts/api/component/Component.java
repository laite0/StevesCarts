package vswe.stevescarts.api.component;

import vswe.stevescarts.impl.entity.CartEntity;

public abstract class Component {

	private final CartEntity cart;
	private final ComponentSettings<Component> settings;

	public Component(InitData data) {
		this.cart = data.cart;
		this.settings = data.componentSettings;
	}

	public CartEntity getCart() {
		return cart;
	}

	public ComponentSettings<Component> getSettings() {
		return settings;
	}

	public static class InitData {
		final CartEntity cart;
		final ComponentSettings<Component> componentSettings;

		private InitData(CartEntity cart, ComponentSettings<Component> componentSettings) {
			this.cart = cart;
			this.componentSettings = componentSettings;
		}

		public static InitData create(CartEntity cart, ComponentSettings<Component> componentSettings) {
			return new InitData(cart, componentSettings);
		}
	}
}
