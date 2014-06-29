package vswe.stevesvehicles.modules.data.registries;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import vswe.stevesvehicles.client.rendering.models.ModelEggBasket;
import vswe.stevesvehicles.client.rendering.models.ModelExtractingChests;
import vswe.stevesvehicles.client.rendering.models.ModelFrontChest;
import vswe.stevesvehicles.client.rendering.models.ModelGiftStorage;
import vswe.stevesvehicles.client.rendering.models.ModelSideChests;
import vswe.stevesvehicles.client.rendering.models.ModelTopChest;
import vswe.stevesvehicles.localization.ILocalizedText;
import vswe.stevesvehicles.modules.ModuleBase;
import vswe.stevesvehicles.modules.data.ModuleData;
import vswe.stevesvehicles.modules.data.ModuleRegistry;
import vswe.stevesvehicles.modules.data.ModuleSide;
import vswe.stevesvehicles.old.Helpers.GiftItem;
import vswe.stevesvehicles.old.Helpers.Localization;
import vswe.stevesvehicles.old.Modules.Storages.Chests.ModuleEggBasket;
import vswe.stevesvehicles.old.Modules.Storages.Chests.ModuleExtractingChests;
import vswe.stevesvehicles.old.Modules.Storages.Chests.ModuleFrontChest;
import vswe.stevesvehicles.old.Modules.Storages.Chests.ModuleGiftStorage;
import vswe.stevesvehicles.old.Modules.Storages.Chests.ModuleSideChests;
import vswe.stevesvehicles.old.Modules.Storages.Chests.ModuleTopChest;
import vswe.stevesvehicles.old.StevesVehicles;
import vswe.stevesvehicles.vehicles.VehicleRegistry;

import java.util.ArrayList;
import java.util.Random;

import static vswe.stevesvehicles.old.Helpers.ComponentTypes.*;


public class ModuleRegistryChests extends ModuleRegistry {
    public ModuleRegistryChests() {
        super("steves_vehicles_chests");

        ModuleData side = new ModuleData("side_chests", ModuleSideChests.class, 3) {
            @Override
            @SideOnly(Side.CLIENT)
            public void loadModels() {
                addModel("SideChest", new ModelSideChests());
            }
        };

        side.addShapedRecipe(   HUGE_CHEST_PANE,        CHEST_PANE,         HUGE_CHEST_PANE,
                                LARGE_CHEST_PANE,       CHEST_LOCK,         LARGE_CHEST_PANE,
                                HUGE_CHEST_PANE,        CHEST_PANE,         HUGE_CHEST_PANE);


        side.addSides(ModuleSide.LEFT, ModuleSide.RIGHT);
        side.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        register(side);



        ModuleData top = new ModuleData("top_chest", ModuleTopChest.class, 6) {
            @Override
            @SideOnly(Side.CLIENT)
            public void loadModels() {
                removeModel("Top");
                addModel("TopChest", new ModelTopChest());
            }
        };

        top.addShapedRecipe(    HUGE_CHEST_PANE,        HUGE_CHEST_PANE,        HUGE_CHEST_PANE,
                                CHEST_PANE,             CHEST_LOCK,             CHEST_PANE,
                                HUGE_CHEST_PANE,        HUGE_CHEST_PANE,        HUGE_CHEST_PANE);


        top.addSides(ModuleSide.TOP);
        top.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        register(top);



        ModuleData front = new ModuleData("front_chest", ModuleFrontChest.class, 5) {
            @Override
            @SideOnly(Side.CLIENT)
            public void loadModels() {
                addModel("FrontChest", new ModelFrontChest());
                setModelMultiplier(0.68F);
            }
        };

        front.addShapedRecipe(  CHEST_PANE,             LARGE_CHEST_PANE,           CHEST_PANE,
                                CHEST_PANE,             CHEST_LOCK,                 CHEST_PANE,
                                LARGE_CHEST_PANE,       LARGE_CHEST_PANE,           LARGE_CHEST_PANE);


        front.addSides(ModuleSide.FRONT);
        front.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        register(front);


        ModuleData internal = new ModuleData("internal_storage", ModuleFrontChest.class, 5);
        internal.addShapedRecipe(   CHEST_PANE,       CHEST_PANE,       CHEST_PANE,
                                    CHEST_PANE,       CHEST_LOCK,       CHEST_PANE,
                                    CHEST_PANE,       CHEST_PANE,       CHEST_PANE);


        internal.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        internal.setAllowDuplicate(true);
        register(internal);


        ModuleData extracting = new ModuleData("extracting_chests", ModuleExtractingChests.class, 75) {
            @Override
            @SideOnly(Side.CLIENT)
            public void loadModels() {
                addModel("SideChest", new ModelExtractingChests());
            }
        };

        extracting.addShapedRecipe(     HUGE_IRON_PANE,        HUGE_IRON_PANE,          HUGE_IRON_PANE,
                                        LARGE_IRON_PANE,       CHEST_LOCK,              LARGE_IRON_PANE,
                                        HUGE_DYNAMIC_PANE,     LARGE_DYNAMIC_PANE,      HUGE_DYNAMIC_PANE);


        extracting.addSides(ModuleSide.LEFT, ModuleSide.RIGHT, ModuleSide.CENTER);
        extracting.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        register(extracting);



        ModuleData basket = new ModuleDataTreatStorage("egg_basket", ModuleEggBasket.class, 14, Localization.MODULE_INFO.EGG_STORAGE_FULL) {
            @Override
            @SideOnly(Side.CLIENT)
            public void loadModels() {
                addModel("TopChest", new ModelEggBasket());
            }

            @Override
            protected void spawnTreat(ModuleBase module) {
                Random rand = module.getVehicle().getRandom();
                int eggs = 1 + rand.nextInt(4) + rand.nextInt(4);
                ItemStack easterEgg = PAINTED_EASTER_EGG.getItemStack(eggs);
                module.setStack(0, easterEgg);
            }
        };

        basket.addShapedRecipe(     new ItemStack(Blocks.wool, 1, 4),       new ItemStack(Blocks.wool, 1, 4),           new ItemStack(Blocks.wool, 1, 4),
                                    EXPLOSIVE_EASTER_EGG,                   CHEST_LOCK,                                 BURNING_EASTER_EGG,
                                    GLISTERING_EASTER_EGG,                  BASKET,                                     CHOCOLATE_EASTER_EGG);


        basket.addSides(ModuleSide.TOP);
        basket.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        register(basket);

        if (!StevesVehicles.isEaster) {
            basket.lock();
        }


        ModuleData gift = new ModuleDataTreatStorage("gift_storage", ModuleGiftStorage.class, 12, Localization.MODULE_INFO.GIFT_STORAGE_FULL) {
            @Override
            @SideOnly(Side.CLIENT)
            public void loadModels() {
                addModel("SideChest", new ModelGiftStorage());
            }

            @Override
            protected void spawnTreat(ModuleBase module) {
                Random rand = module.getVehicle().getRandom();
                ArrayList<ItemStack> items = GiftItem.generateItems(rand, GiftItem.ChristmasList, 50 + rand.nextInt(700), 1 + rand.nextInt(5));
                for (int i = 0; i < items.size(); i++) {
                    module.getVehicle().setStack(i, items.get(i));
                }
            }
        };

        gift.addShapedRecipe(   YELLOW_GIFT_RIBBON,       null,             RED_GIFT_RIBBON,
                                RED_WRAPPING_PAPER,       CHEST_LOCK,       GREEN_WRAPPING_PAPER,
                                RED_WRAPPING_PAPER,      STUFFED_SOCK,      GREEN_WRAPPING_PAPER);


        gift.addSides(ModuleSide.LEFT, ModuleSide.RIGHT);
        gift.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
        register(gift);

        if (!StevesVehicles.isChristmas) {
            gift.lock();
        }

    }

    private static final String STORAGE_OPENED = "Opened";
    private static abstract class ModuleDataTreatStorage extends ModuleData {
        private ILocalizedText fullText;
        public ModuleDataTreatStorage(String unlocalizedName, Class<? extends ModuleBase> moduleClass, int modularCost, ILocalizedText fullText) {
            super(unlocalizedName, moduleClass, modularCost);
            setHasExtraData(true);
            this.fullText = fullText;
        }

        @Override
        public void addDefaultExtraData(NBTTagCompound compound) {
            compound.setBoolean(STORAGE_OPENED, false);
        }

        @Override
        public void addExtraData(NBTTagCompound compound, ModuleBase module) {
            compound.setBoolean(STORAGE_OPENED, true);
        }

        @Override
        public void readExtraData(NBTTagCompound compound, ModuleBase moduleBase) {
            if (!compound.getBoolean(STORAGE_OPENED)) {
                spawnTreat(moduleBase);
            }
        }

        @Override
        public String getCartInfoText(String name, NBTTagCompound compound) {
            if (compound.getBoolean(STORAGE_OPENED)) {
                return Localization.MODULE_INFO.STORAGE_EMPTY.translate() + " " + name;
            }else{
                return Localization.MODULE_INFO.STORAGE_FULL.translate() + " " + name;
            }
        }

        @Override
        public String getModuleInfoText(NBTTagCompound compound) {
            if (compound.getBoolean(STORAGE_OPENED)) {
                return Localization.MODULE_INFO.STORAGE_EMPTY.translate();
            }else{
                return fullText.translate();
            }
        }

        protected abstract void spawnTreat(ModuleBase module);
    }
}