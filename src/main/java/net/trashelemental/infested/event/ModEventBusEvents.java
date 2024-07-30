package net.trashelemental.infested.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.CrimsonBeetleEntity;
import net.trashelemental.infested.entity.custom.GrubEntity;
import net.trashelemental.infested.infested;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CRIMSON_BEETLE.get(), CrimsonBeetleEntity.createAttributes().build());
        event.put(ModEntities.GRUB.get(), GrubEntity.createAttributes().build());
    }


}
