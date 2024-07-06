package net.trashelemental.infested.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, infested.MOD_ID);

    public static final RegistryObject<Item> CHITIN = ITEMS.register("chitin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INSECT_TEMPLATE = ITEMS.register("insect_template",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_TEMPLATE = ITEMS.register("spider_template",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_GRUB = ITEMS.register("raw_grub",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FRIED_GRUB = ITEMS.register("fried_grub",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BUG_STEW = ITEMS.register("bug_stew",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SILVERFISH_EGGS = ITEMS.register("silverfish_eggs",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
