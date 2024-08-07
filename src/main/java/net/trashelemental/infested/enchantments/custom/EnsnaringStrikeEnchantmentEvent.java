package net.trashelemental.infested.enchantments.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.enchantments.ModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EnsnaringStrikeEnchantmentEvent {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
            execute(event, event.getEntity().level(), event.getEntity(), sourceEntity);
        }
    }

    private static void execute(@Nullable Event event, Level world, Entity entity, LivingEntity sourceEntity) {
        if (entity == null || sourceEntity == null) return;

        ItemStack weapon = sourceEntity.getMainHandItem();
        if (weapon.isEmpty()) return;

        int enchantmentLevel = EnchantmentHelper.getEnchantments(weapon)
                .getOrDefault(ModEnchantments.ENSNARING_STRIKE.get(), 0);

        if (enchantmentLevel > 0 && entity instanceof LivingEntity target) {
            int duration = 20 * enchantmentLevel;
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 3));

            if (!(sourceEntity instanceof Player) || !((Player) sourceEntity).getAbilities().instabuild) {
                weapon.hurtAndBreak(5, sourceEntity, (e) -> e.broadcastBreakEvent(sourceEntity.getUsedItemHand()));
            }
        }
    }
}
