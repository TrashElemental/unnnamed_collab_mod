package net.team_us.collab_mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import net.team_us.collab_mod.entity.custom.minions.LarvaMinionEntity;
import net.team_us.collab_mod.entity.custom.projectiles.HoneymanProjectileEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CollabMod.MOD_ID);

    public static final RegistryObject<EntityType<HoneymanEntity>> HONEYMAN =
            ENTITY_TYPES.register("honeyman", () -> EntityType.Builder.of(HoneymanEntity::new, MobCategory.MONSTER)
                    .sized(1.4f, 3.3f).build("honeyman"));
    public static final RegistryObject<EntityType<BeeMinionEntity>> HONEYMAN_BEE_MINION =
            ENTITY_TYPES.register("honeyman_minion_bee", () -> EntityType.Builder.of(BeeMinionEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("honeyman_minion_bee"));
    public static final RegistryObject<EntityType<LarvaMinionEntity>> HONEYMAN_LARVA_MINION =
            ENTITY_TYPES.register("honeyman_minion_larva", () -> EntityType.Builder.of(LarvaMinionEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f).build("honeyman_minion_larva"));



    //Projectile
    public static final RegistryObject<EntityType<HoneymanProjectileEntity>> HONEYMAN_PROJECTILE_ENTITY =
            ENTITY_TYPES.register("honeyman_projectile_entity",
                    () -> EntityType.Builder.<HoneymanProjectileEntity>of(HoneymanProjectileEntity::new, MobCategory.MISC)
                            .sized(0.3f, 0.3f).build("honeyman_projectile_entity"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
