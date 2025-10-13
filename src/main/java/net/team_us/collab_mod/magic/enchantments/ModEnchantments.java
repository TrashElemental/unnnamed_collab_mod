package net.team_us.collab_mod.magic.enchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.team_us.collab_mod.CollabMod;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(Registries.ENCHANTMENT, CollabMod.MOD_ID);




    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
