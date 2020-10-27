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




    RAW_HARDENER,
    REFINED_HARDENER,
    HARDENED_MESH,
    STABILIZED_METAL,
    REINFORCED_METAL,



    SOLAR_PANEL,
    ADVANCED_SOLAR_PANEL,

    ;

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
