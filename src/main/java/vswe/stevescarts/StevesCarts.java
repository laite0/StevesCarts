package vswe.stevescarts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import vswe.stevescarts.impl.entity.CartEntity;
import vswe.stevescarts.impl.item.ItemCart;
import vswe.stevescarts.impl.packets.ClientBoundPackets;

public class StevesCarts implements ModInitializer {

	public static final String MOD_ID = "stevescarts";
	public static EntityType<Entity> cartEntityType;
	public static final StevesCartsManager manager = new StevesCartsManager();

	@Override
	public void onInitialize() {
		cartEntityType = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "cart"),
				FabricEntityTypeBuilder.create(EntityCategory.MISC, CartEntity::new)
						.size(EntityDimensions.fixed(1, 1))
						.build()
		);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "cart"), new ItemCart());

		ClientBoundPackets.init();
		manager.load();


	}
}
