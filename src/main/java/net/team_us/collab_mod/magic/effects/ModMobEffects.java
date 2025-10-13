package net.team_us.collab_mod.magic.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.team_us.collab_mod.CollabMod;

public class ModMobEffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CollabMod.MOD_ID);




    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
