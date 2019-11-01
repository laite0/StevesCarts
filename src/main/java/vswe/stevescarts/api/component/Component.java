package vswe.stevescarts.api.component;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import org.apache.commons.lang3.Validate;
import vswe.stevescarts.api.StevesCart;
import vswe.stevescarts.impl.entity.CartEntity;

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

	protected static <T> TrackedData<T> createTracked(TrackedDataHandler<T> handler) {
		return DataTracker.registerData(CartEntity.class, handler);
	}

	protected <T> T getData(TrackedData<T> dataTracker) {
		return cart.getDataTracker().get(dataTracker);
	}

	protected <T> void setData(TrackedData<T> dataTracker, T t) {
		Validate.notNull(dataTracker);
		Validate.notNull(t);
		Validate.notNull(cart.getDataTracker());
		cart.getDataTracker().set(dataTracker, t);
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
