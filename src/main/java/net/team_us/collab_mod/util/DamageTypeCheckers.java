package net.team_us.collab_mod.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

/**
 * This class has some methods to check for certain damage types that are harder to get or
 * can be multiple different damage types, like fire or smite.
 */
public class DamageTypeCheckers {

    public static boolean isSmiteDamage(DamageSource source) {
        boolean isSmite = false;
        Entity attacker = source.getEntity();

        if (attacker instanceof LivingEntity entity) {
            int smiteLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, entity.getMainHandItem());
            isSmite = smiteLevel > 0;
        }

        return isSmite;
    }

    public static boolean isFireDamage(DamageSource source) {
        return source.is(DamageTypes.IN_FIRE) ||
                source.is(DamageTypes.ON_FIRE) ||
                source.is(DamageTypes.FIREBALL) ||
                source.is(DamageTypes.UNATTRIBUTED_FIREBALL) ||
                source.is(DamageTypes.LAVA);
    }

}
