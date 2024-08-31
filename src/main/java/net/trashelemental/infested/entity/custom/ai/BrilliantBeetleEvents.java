package net.trashelemental.infested.entity.custom.ai;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.custom.BrilliantBeetleEntity;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber
public class BrilliantBeetleEvents {

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getSource(), event.getEntity());
        }
    }

    public static void execute(DamageSource damagesource, Entity entity) {
        execute(null, damagesource, entity);
    }

    private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {

        //Cancels fall damage if the player is riding a Brilliant Beetle
        if (damagesource == null || entity == null)
            return;
        if (damagesource.is(DamageTypes.FALL) && entity.isPassenger()) {
            if ((entity.getVehicle()) instanceof BrilliantBeetleEntity) {
                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}

