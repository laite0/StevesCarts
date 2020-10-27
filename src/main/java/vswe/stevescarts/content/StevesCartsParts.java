package vswe.stevescarts.content;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.StevesCarts;

import java.util.Locale;

public enum StevesCartsParts implements ItemConvertible {

        ADVANCED_SOLAR_PANEL,

        HARDENED_MESH,

        IRON_WHEELS,

        RAW_HARDENER,
        REFINED_HARDENER,
        REINFORCED_METAL,
        REINFORCED_WHEELS,

        SIMPLE_PCB,
        SOLAR_PANEL,
        STABILIZED_METAL,
        WOODEN_WHEELS;

        public final String name;
        public final Item item;

        StevesCartsParts(){
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
