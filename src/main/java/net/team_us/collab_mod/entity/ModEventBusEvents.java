package net.team_us.collab_mod.entity;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.custom.bosses.TestBossNoBar;
import net.team_us.collab_mod.entity.custom.bosses.TestBossWithBar;

@Mod.EventBusSubscriber(modid = CollabMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

        event.put(ModEntities.TEST_BOSS_BAR.get(), TestBossWithBar.createAttributes().build());
        event.put(ModEntities.TEST_BOSS_NO_BAR.get(), TestBossNoBar.createAttributes().build());

    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {



    }

;


    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {


    }


}
