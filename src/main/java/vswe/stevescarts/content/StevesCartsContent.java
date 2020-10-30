package vswe.stevescarts.content;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.StevesCartsAPI;
import vswe.stevescarts.api.StevesCartsInitializer;
import vswe.stevescarts.api.component.ComponentType;
import vswe.stevescarts.api.listeners.CartTick;
import vswe.stevescarts.content.client.components.FarmerRenderer;
import vswe.stevescarts.content.client.components.LawnMowerModel;
import vswe.stevescarts.content.client.components.SolarEngineRenderer;
import vswe.stevescarts.content.components.FarmerComponent;
import vswe.stevescarts.content.components.LawnMowerComponent;
import vswe.stevescarts.content.components.SolarEngineComponent;

import java.util.Arrays;

public class StevesCartsContent implements StevesCartsInitializer {

	@Override
	public void onInitialize(StevesCartsAPI api) {
		api.addComponent(settings -> {
			settings.setId(new Identifier(StevesCarts.MOD_ID, "solar_engine"));
			settings.setType(ComponentType.MODULE);
			settings.renderer(new SolarEngineRenderer());

			settings.addListener(CartTick.class, SolarEngineComponent::tick);
		}, SolarEngineComponent::new);

		api.addComponent(settings -> {
			settings.setId(new Identifier(StevesCarts.MOD_ID, "lawn_mower"));
			settings.setType(ComponentType.MODULE);
			settings.renderer(new LawnMowerModel());

			settings.addListener(CartTick.class, LawnMowerComponent::tick);
		}, LawnMowerComponent::new);

		api.addComponent(settings -> {
			settings.setId(new Identifier(StevesCarts.MOD_ID, "farmer"));
			settings.setType(ComponentType.MODULE);
			settings.renderer(new FarmerRenderer());

			settings.addListener(CartTick.class, FarmerComponent::tick);
		}, FarmerComponent::new);

		Arrays.stream(StevesCartsBlocks.values()).forEach(value -> registerBlock(value.block, value.name));
		Arrays.stream(StevesCartsParts.values()).forEach(value -> registerItem(value.item, value.name));
	}

	private void registerItem(Item item, String name){
		Registry.register(Registry.ITEM, new Identifier(StevesCarts.MOD_ID, name), item);
	}

	private void registerBlock(Block block, String name){
		Identifier identifier = new Identifier(StevesCarts.MOD_ID, name);
		Registry.register(Registry.BLOCK, identifier, block);
		BlockItem itemBlock = new BlockItem(block, new Item.Settings().group(StevesCarts.SC2BLOCKS));
		Registry.register(Registry.ITEM, identifier, itemBlock);
	}
}
