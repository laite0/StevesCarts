package vswe.stevescarts.content;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.StevesCarts;

import java.util.Locale;

public enum StevesCartsParts implements ItemConvertible {

    WOODEN_WHEELS,
    IRON_WHEELS,
    REINFORCED_WHEELS,
    GALGADORIAN_WHEELS,

    RED_PIGMENT,
    GREEN_PIGMENT,
    BLUE_PIGMENT,

    GLASS_O_MAGIC,
    FUSE,
    DYNAMITE,

    SIMPLE_PCB,
    ADVANCED_PCB,
    GRAPHICAL_INTERFACE,

    RAW_HANDLE,
    REFINED_HANDLE,
    SPEED_HANDLE,
    WHEEL,


    PIPE,
    SAW_BLADE,
    HARDENED_SAW_BLADE,
    GALGADORIAN_SAW_BLADE,
    WOOD_CUTTING_CORE,
    SHOOTING_STATION,
    ENTITY_ANALYZER,
    ENTITY_SCANNER,
    EMPTY_DISK,
    TRI_TORCH,
    CHEST_PANE,
    LARGE_CHEST_PANE,
    HUGE_CHEST_PANE,
    CHEST_LOCK,
    IRON_PANE,
    LARGE_IRON_PANE,
    HUGE_IRON_PANE,
    DYNAMIC_PANE,
    LARGE_DYNAMIC_PANE,
    HUGE_DYNAMIC_PANE,
    CLEANING_FAN,
    CLEANING_TUBE,
    CLEANING_CORE,
    LIQUID_CLEANING_TUBE,
    LIQUID_CLEANING_CORE,
    RAW_HARDENER,
    REFINED_HARDENER,
    HARDENED_MESH,
    STABILIZED_METAL,
    REINFORCED_METAL,
    EYE_OF_GALGADOR,
    LUMP_OF_GALGADOR,
    LARGE_LUMP_OF_GALGADOR,
    GALGADORIAN_METAL,
    ENHANCED_GALGADORIAN_METAL,

    SOLAR_PANEL,
    ADVANCED_SOLAR_PANEL,
    BLANK_UPGRADE,
    TANK_VALVE,
    TANK_PANE,
    LARGE_TANK_PANE,
    HUGE_TANK_PANE,

    IRON_BLADE,
    BLADE_ARM;

    public final String name;
    public final Item item;

    StevesCartsParts() {
        name = this.toString().toLowerCase(Locale.ROOT);
        item = new Item(new Item.Settings().group(StevesCarts.SC2PARTS));
    }

    public ItemStack getStack() {
        return new ItemStack(item);
    }

    public ItemStack getStack(int amount) {
        return new ItemStack(item, amount);
    }

    @Override
    public Item asItem() {
        return item;
    }

}
