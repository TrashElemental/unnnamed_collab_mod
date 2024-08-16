package net.trashelemental.infested.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.*;
import net.trashelemental.infested.entity.custom.jewelbeetles.AncientDebreetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.ChorusBeetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.HarvestBeetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.JewelBeetleEntity;
import net.trashelemental.infested.entity.custom.silverfish.AttackSilverfishEntity;
import net.trashelemental.infested.entity.custom.silverfish.TamedSilverfishEntity;
import net.trashelemental.infested.entity.custom.spiders.AttackSpiderEntity;
import net.trashelemental.infested.entity.custom.spiders.SpiderMinionEntity;
import net.trashelemental.infested.entity.custom.spiders.TamedSpiderEntity;
import net.trashelemental.infested.infested;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CRIMSON_BEETLE.get(), CrimsonBeetleEntity.createAttributes().build());
        event.put(ModEntities.GRUB.get(), GrubEntity.createAttributes().build());
        event.put(ModEntities.BRILLIANT_BEETLE.get(), BrilliantBeetleEntity.createAttributes().build());
        event.put(ModEntities.MANTIS.get(), MantisEntity.createAttributes().build());

        event.put(ModEntities.HARVEST_BEETLE.get(), HarvestBeetleEntity.createAttributes().build());
        event.put(ModEntities.JEWEL_BEETLE.get(), JewelBeetleEntity.createAttributes().build());
        event.put(ModEntities.CHORUS_BEETLE.get(), ChorusBeetleEntity.createAttributes().build());
        event.put(ModEntities.ANCIENT_DEBREETLE.get(), AncientDebreetleEntity.createAttributes().build());

        event.put(ModEntities.TAMED_SILVERFISH.get(), TamedSilverfishEntity.createAttributes().build());
        event.put(ModEntities.ATTACK_SILVERFISH.get(), AttackSilverfishEntity.createAttributes().build());
        event.put(ModEntities.TAMED_SPIDER.get(), TamedSpiderEntity.createAttributes().build());
        event.put(ModEntities.SPIDER_MINION.get(), SpiderMinionEntity.createAttributes().build());
        event.put(ModEntities.ATTACK_SPIDER.get(), AttackSpiderEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

        event.register(ModEntities.MANTIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MantisEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.BRILLIANT_BEETLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BrilliantBeetleEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.CRIMSON_BEETLE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CrimsonBeetleEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.GRUB.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GrubEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

    }


}
