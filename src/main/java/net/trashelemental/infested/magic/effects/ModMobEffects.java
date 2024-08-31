package net.trashelemental.infested.magic.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.magic.effects.custom.AmbushCooldownMobEffect;
import net.trashelemental.infested.magic.effects.custom.AmbushMobEffect;
import net.trashelemental.infested.magic.effects.custom.ParasiticInfectionMobEffect;

public class ModMobEffects {

    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, infested.MOD_ID);

    public static final RegistryObject<MobEffect> AMBUSH = REGISTRY.register("ambush", AmbushMobEffect::new);
    public static final RegistryObject<MobEffect> AMBUSH_COOLDOWN = REGISTRY.register("ambush_cooldown", AmbushCooldownMobEffect::new);
    public static final RegistryObject<MobEffect> PARASITIC_INFECTION = REGISTRY.register("parasitic_infection", ParasiticInfectionMobEffect::new);


    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
