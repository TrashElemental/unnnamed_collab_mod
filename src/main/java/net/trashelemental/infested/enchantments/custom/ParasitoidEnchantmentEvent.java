package net.trashelemental.infested.enchantments.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.enchantments.ModEnchantments;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.infested;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ParasitoidEnchantmentEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(LevelAccessor world, Entity entity, Entity sourceEntity) {
        execute(null, world, entity, sourceEntity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, @Nullable Entity sourceEntity) {
        if (entity == null || sourceEntity == null)
            return;

        if (!(sourceEntity instanceof LivingEntity livingSource))
            return;

        ItemStack weapon = livingSource.getMainHandItem();
        int enchantmentLevel = weapon.getEnchantmentLevel(ModEnchantments.PARASITOID.get());

        if (enchantmentLevel > 0) {
            infested.queueServerWork(20, () -> {
                if (world instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < enchantmentLevel; i++) {
                        Entity silverfishEntity = ModEntities.TAMED_SILVERFISH.get().create(serverLevel);
                        if (silverfishEntity instanceof TamableAnimal tamedSilverfish) {
                            tamedSilverfish.setPos(entity.getX(), entity.getY(), entity.getZ());
                            tamedSilverfish.setTame(true);
                            tamedSilverfish.setAge(-300);
                            tamedSilverfish.setOwnerUUID(sourceEntity.getUUID());
                            serverLevel.addFreshEntity(tamedSilverfish);
                            serverLevel.playSound(null, entity.blockPosition(),
                                    SoundEvents.TURTLE_EGG_HATCH, SoundSource.NEUTRAL, 1, 3);
                        }
                    }

                    if (sourceEntity instanceof Player player && !player.getAbilities().instabuild) {
                        weapon.hurtAndBreak(2, livingSource, (e) -> {
                            e.broadcastBreakEvent(livingSource.getUsedItemHand());
                        });
                    }
                }
            });
        }
    }
}