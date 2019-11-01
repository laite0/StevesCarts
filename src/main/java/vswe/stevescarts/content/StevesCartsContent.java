package vswe.stevescarts.content;

import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.StevesCartsAPI;
import vswe.stevescarts.api.StevesCartsInitializer;
import vswe.stevescarts.api.component.ComponentType;
import vswe.stevescarts.api.listeners.PlayerInteractListener;
import vswe.stevescarts.api.listeners.TickListener;
import vswe.stevescarts.content.components.CreativeEngineComponent;


public class StevesCartsContent implements StevesCartsInitializer {

	@Override
	public void onInitialize(StevesCartsAPI api) {

		api.addComponent(settings -> {
			settings.setId(new Identifier(StevesCarts.MOD_ID, "creative_engine"));
			settings.setType(ComponentType.MODULE);


			settings.addListener(TickListener.class, CreativeEngineComponent::tick);
			settings.addListener(PlayerInteractListener.class, CreativeEngineComponent::use);
		}, CreativeEngineComponent::new);

	}
}
