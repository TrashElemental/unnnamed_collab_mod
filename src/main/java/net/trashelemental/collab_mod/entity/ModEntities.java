package net.trashelemental.collab_mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.collab_mod.CollabMod;
import net.trashelemental.collab_mod.entity.custom.bosses.TestBossNoBar;
import net.trashelemental.collab_mod.entity.custom.bosses.TestBossWithBar;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CollabMod.MOD_ID);


    public static final RegistryObject<EntityType<TestBossWithBar>> TEST_BOSS_BAR =
            ENTITY_TYPES.register("test_boss_with_bar", () -> EntityType.Builder.of(TestBossWithBar::new, MobCategory.MONSTER)
                    .sized(1f, 2.5f).build("test_boss_with_bar"));

    public static final RegistryObject<EntityType<TestBossNoBar>> TEST_BOSS_NO_BAR =
            ENTITY_TYPES.register("test_boss_no_bar", () -> EntityType.Builder.of(TestBossNoBar::new, MobCategory.MONSTER)
                    .sized(1f, 2.5f).build("test_boss_no_bar"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
