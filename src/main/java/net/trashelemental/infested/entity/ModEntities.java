package net.trashelemental.infested.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.entity.custom.CrimsonBeetleEntity;
import net.trashelemental.infested.entity.custom.GrubEntity;
import net.trashelemental.infested.infested;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, infested.MOD_ID);


    public static final RegistryObject<EntityType<CrimsonBeetleEntity>> CRIMSON_BEETLE =
            ENTITY_TYPES.register("crimson_beetle", () -> EntityType.Builder.of(CrimsonBeetleEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1).build("crimson_beetle"));
    public static final RegistryObject<EntityType<GrubEntity>> GRUB =
            ENTITY_TYPES.register("grub", () -> EntityType.Builder.of(GrubEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.5f).build("grub"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
