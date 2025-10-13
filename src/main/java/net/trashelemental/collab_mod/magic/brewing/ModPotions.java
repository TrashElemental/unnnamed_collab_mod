package net.trashelemental.collab_mod.magic.brewing;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.collab_mod.CollabMod;

public class ModPotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, CollabMod.MOD_ID);





    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
