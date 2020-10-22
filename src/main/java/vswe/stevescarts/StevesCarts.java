package vswe.stevescarts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import vswe.stevescarts.impl.client.CartEntityRenderer;
import vswe.stevescarts.impl.client.gui.CartScreenHandler;
import vswe.stevescarts.impl.client.gui.CartScreen;
import vswe.stevescarts.impl.entity.CartEntity;
import vswe.stevescarts.impl.item.ItemCart;
import vswe.stevescarts.impl.network.ClientBoundPackets;

public class StevesCarts implements ModInitializer {

	public static final String MOD_ID = "stevescarts";
	public static EntityType<Entity> cartEntityType;
	public static final StevesCartsManager manager = new StevesCartsManager();

	@Override
	public void onInitialize() {
		cartEntityType = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "cart"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, CartEntity::new)
						.dimensions(EntityDimensions.fixed(1, 1))
						.build()
		);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "cart"), new ItemCart());

		ClientBoundPackets.init();
		EntityRendererRegistry.INSTANCE.register(cartEntityType, (dispatcher, context) -> new CartEntityRenderer(dispatcher));

		ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(MOD_ID, "cart"), (syncID, identifier, playerEntity, packetByteBuf) -> {
			CartEntity cartEntity = (CartEntity) playerEntity.getEntityWorld().getEntityById(packetByteBuf.readInt());
			return new CartScreenHandler(syncID, playerEntity, cartEntity);
		});

		ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier(MOD_ID, "cart"), CartScreen::new);

		manager.load();


	}
}
