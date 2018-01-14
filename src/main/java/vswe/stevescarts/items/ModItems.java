package vswe.stevescarts.items;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import vswe.stevescarts.Constants;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.blocks.ModBlocks;
import vswe.stevescarts.helpers.ComponentTypes;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public final class ModItems {
	public final static Set<Item> ITEMS = new LinkedHashSet<>();

	public static ItemCarts CARTS = new ItemCarts();
	public static ItemCartComponent COMPONENTS = new ItemCartComponent();
	public static ItemCartModule MODULES = new ItemCartModule();

	public static void preInit() {
		registerItem(ModItems.CARTS, "modularcart");
		registerItem(ModItems.COMPONENTS, "modulecomponents");
		registerItem(ModItems.MODULES, "cartmodule");
	}

	public static void registerItem(Item item, String name) {
		ITEMS.add(item);
		item.setRegistryName(Constants.MOD_ID, name);
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		for (Item item : ModBlocks.ITEM_BLOCKS)
			registry.register(item);

		for (Item item: ITEMS)
			registry.register(item);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Item item : ITEMS) {
			StevesCarts.proxy.registerDefaultItemRenderer(item);
		}
	}

	public static void addRecipes() {
		FurnaceRecipes.instance().addSmeltingRecipe(ComponentTypes.RAW_HANDLE.getItemStack(), ComponentTypes.REFINED_HANDLE.getItemStack(), 0.0f);
		FurnaceRecipes.instance().addSmeltingRecipe(ComponentTypes.RAW_HARDENER.getItemStack(), ComponentTypes.REFINED_HARDENER.getItemStack(), 0.0f);
		FurnaceRecipes.instance().addSmeltingRecipe(ComponentTypes.STABILIZED_METAL.getItemStack(), ComponentTypes.REINFORCED_METAL.getItemStack(), 0.0f);
		FurnaceRecipes.instance().addSmeltingRecipe(ComponentTypes.LUMP_OF_GALGADOR.getItemStack(), ComponentTypes.GALGADORIAN_METAL.getItemStack(), 0.0f);
		FurnaceRecipes.instance().addSmeltingRecipe(ComponentTypes.LARGE_LUMP_OF_GALGADOR.getItemStack(), ComponentTypes.ENHANCED_GALGADORIAN_METAL.getItemStack(), 0.0f);
	}

	public interface IMultipleItemModelDefinition {
		/**
		 * A map from item meta values to different item models
		 * @return
		 */
		@SideOnly(Side.CLIENT)
		Map<Integer, ResourceLocation> getModels();
	}
}
