package vswe.stevescarts.content;

import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.StevesCartsAPI;
import vswe.stevescarts.api.StevesCartsInitializer;
import vswe.stevescarts.api.component.ComponentType;
import vswe.stevescarts.api.listeners.CartDataTracker;
import vswe.stevescarts.api.listeners.CartTick;
import vswe.stevescarts.api.listeners.PlayerInteract;
import vswe.stevescarts.content.client.components.LawnMowerRenderer;
import vswe.stevescarts.content.client.components.SolarEngineRenderer;
import vswe.stevescarts.content.components.LawnMowerComponent;
import vswe.stevescarts.content.components.SolarEngineComponent;

public class StevesCartsContent implements StevesCartsInitializer {

	@Override
	public void onInitialize(StevesCartsAPI api) {
		api.addComponent(settings -> {
			settings.setId(new Identifier(StevesCarts.MOD_ID, "solar_engine"));
			settings.setType(ComponentType.MODULE);
			settings.renderer(new SolarEngineRenderer());

			settings.addListener(CartTick.class, SolarEngineComponent::tick);
			settings.addListener(CartDataTracker.class, SolarEngineComponent::initDataTracker);
			settings.addListener(PlayerInteract.class, SolarEngineComponent::use);
		}, SolarEngineComponent::new);

		api.addComponent(settings -> {
			settings.setId(new Identifier(StevesCarts.MOD_ID, "lawn_mower"));
			settings.setType(ComponentType.MODULE);
			settings.renderer(new LawnMowerRenderer());

			settings.addListener(CartTick.class, LawnMowerComponent::tick);
		}, LawnMowerComponent::new);

	}
}
