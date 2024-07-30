package net.trashelemental.infested.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.custom.BugStewItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, infested.MOD_ID);

    // Crafting Ingredients
    public static final RegistryObject<Item> CHITIN = ITEMS.register("chitin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INSECT_TEMPLATE = ITEMS.register("insect_template",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_TEMPLATE = ITEMS.register("spider_template",
            () -> new Item(new Item.Properties()));


    // Foods
    public static final RegistryObject<Item> RAW_GRUB = ITEMS.register("raw_grub",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_GRUB)));

    public static final RegistryObject<Item> FRIED_GRUB = ITEMS.register("fried_grub",
            () -> new Item(new Item.Properties().food(ModFoods.FRIED_GRUB)));

    public static final RegistryObject<Item> BUG_STEW = ITEMS.register("bug_stew",
            () -> new BugStewItem(new Item.Properties().food(ModFoods.BUG_STEW)));


    //Functional Items
    public static final RegistryObject<Item> SILVERFISH_EGGS = ITEMS.register("silverfish_eggs",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg",
            () -> new Item(new Item.Properties()));

    //Armor Items
    public static final RegistryObject<Item> CHITIN_HELMET = ITEMS.register("chitin_helmet",
            () -> new ArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> CHITIN_CHESTPLATE = ITEMS.register("chitin_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> CHITIN_LEGGINGS = ITEMS.register("chitin_leggings",
            () -> new ArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> CHITIN_BOOTS = ITEMS.register("chitin_boots",
            () -> new ArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_HELMET = ITEMS.register("spider_helmet",
            () -> new ArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_CHESTPLATE = ITEMS.register("spider_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_LEGGINGS = ITEMS.register("spider_leggings",
            () -> new ArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_BOOTS = ITEMS.register("spider_boots",
            () -> new ArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.BOOTS, new Item.Properties()));

    //Spawn Eggs
    public static final RegistryObject<Item> CRIMSON_BEETLE_SPAWN_EGG = ITEMS.register("crimson_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRIMSON_BEETLE, -7271926, -14415607, new Item.Properties()));
    public static final RegistryObject<Item> GRUB_SPAWN_EGG = ITEMS.register("grub_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GRUB, -3361401, -7720655, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
