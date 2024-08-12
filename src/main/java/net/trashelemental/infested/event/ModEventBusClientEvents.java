package net.trashelemental.infested.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.client.*;
import net.trashelemental.infested.entity.client.models.*;
import net.trashelemental.infested.infested;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CRIMSON_BEETLE_LAYER, CrimsonBeetleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GRUB_LAYER, GrubModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BRILLIANT_BEETLE_LAYER, BrilliantBeetleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MANTIS_LAYER, MantisModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.HARVEST_BEETLE_LAYER, JewelBeetleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.JEWEL_BEETLE_LAYER, JewelBeetleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CHORUS_BEETLE_LAYER, JewelBeetleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ANCIENT_DEBREETLE_LAYER, JewelBeetleModel::createBodyLayer);
    }
}

