package net.team_us.collab_mod.entity;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import net.team_us.collab_mod.entity.custom.minions.LarvaMinionEntity;

@Mod.EventBusSubscriber(modid = CollabMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

        event.put(ModEntities.HONEYMAN.get(), HoneymanEntity.createAttributes().build());
        event.put(ModEntities.HONEYMAN_BEE_MINION.get(), BeeMinionEntity.createAttributes().build());
        event.put(ModEntities.HONEYMAN_LARVA_MINION.get(), LarvaMinionEntity.createAttributes().build());

    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {



    }

;


    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {


    }


}
