package net.trashelemental.collab_mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.collab_mod.CollabMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CollabMod.MOD_ID);




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
