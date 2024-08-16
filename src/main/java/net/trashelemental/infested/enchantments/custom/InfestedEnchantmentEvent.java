package net.trashelemental.infested.enchantments.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.enchantments.ModEnchantments;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.silverfish.TamedSilverfishEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class InfestedEnchantmentEvent {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
            execute(event, event.getEntity().level(), event.getEntity(), sourceEntity);
        }
    }

    private static void execute(@Nullable Event event, Level world, Entity entity, LivingEntity sourceEntity) {
        if (entity == null || sourceEntity == null) return;

        if (entity instanceof LivingEntity livingEntity) {
            ItemStack chestArmor = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
            int enchantmentLevel = chestArmor.getEnchantmentLevel(ModEnchantments.INFESTED.get());

            if (enchantmentLevel > 0) {

                world.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                        SoundEvents.BEEHIVE_EXIT, SoundSource.NEUTRAL, 1.0F, 1.0F);

                if (world instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.POOF, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, 0.5, 0.5, 0.5, 0.0);
                }

                for (int i = 0; i < enchantmentLevel; i++) {
                    if (world instanceof ServerLevel serverLevel) {
                        TamedSilverfishEntity silverfish = ModEntities.TAMED_SILVERFISH.get().create(serverLevel);
                        if (silverfish != null) {
                            silverfish.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), world.getRandom().nextFloat() * 360F, 0);
                            silverfish.setTame(true);
                            silverfish.setOwnerUUID(livingEntity.getUUID());
                            silverfish.setAge(-200);
                            serverLevel.addFreshEntity(silverfish);
                        }
                    }
                }

                chestArmor.hurtAndBreak(2, livingEntity, (entity1) -> {
                    entity1.broadcastBreakEvent(EquipmentSlot.CHEST);
                });

            }
        }
    }


    @SubscribeEvent
    public static void whenEntityBlocksWithShield(ShieldBlockEvent event) {
        if (event != null && event.getEntity() != null) {
            executeShieldEvent(event, event.getEntity().level(), event.getEntity());
        }
    }

    private static void executeShieldEvent(@Nullable Event event, Level world, Entity entity) {
        if (entity == null) return;

        if (entity instanceof LivingEntity livingEntity) {
            ItemStack shield = livingEntity.getUseItem();
            int enchantmentLevel = shield.getEnchantmentLevel(ModEnchantments.INFESTED.get());

            if (enchantmentLevel > 0) {
                playInfestedEffect(world, livingEntity, enchantmentLevel);
                shield.hurtAndBreak(2, livingEntity, (e) -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
        }
    }

    private static void playInfestedEffect(Level world, LivingEntity livingEntity, int enchantmentLevel) {
        world.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                SoundEvents.BEEHIVE_EXIT, SoundSource.NEUTRAL, 1.0F, 1.0F);

        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, 0.5, 0.5, 0.5, 0.0);

            for (int i = 0; i < enchantmentLevel; i++) {
                TamedSilverfishEntity silverfish = ModEntities.TAMED_SILVERFISH.get().create(serverLevel);
                if (silverfish != null) {
                    silverfish.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), world.getRandom().nextFloat() * 360F, 0);
                    silverfish.setTame(true);
                    silverfish.setOwnerUUID(livingEntity.getUUID());
                    silverfish.setAge(-200);
                    serverLevel.addFreshEntity(silverfish);
                }
            }
        }
    }
}