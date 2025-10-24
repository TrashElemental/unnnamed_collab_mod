package net.team_us.collab_mod.entity.custom.projectiles;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.team_us.collab_mod.entity.ModEntities;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import net.team_us.collab_mod.junkyard_lib.visual.particle.ParticleMethods;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class HoneymanProjectileEntity extends AbstractArrow implements GeoEntity {

    public HoneymanProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public HoneymanProjectileEntity(LivingEntity shooter, Level level) {
        super(ModEntities.HONEYMAN_PROJECTILE_ENTITY.get(),
                shooter, level);
    }

    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.HONEY_BLOCK_HIT;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.EGG);
    }

    protected ParticleOptions getFollowingParticle() {
        return new DustParticleOptions(new Vector3f(0.702f, 0.823f, 0.471f), 1.1f);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        if (target instanceof HoneymanEntity || target instanceof BeeMinionEntity) {
            return false;
        }
        return super.canHitEntity(target);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();

        super.onHitEntity(result);

        if (!this.level().isClientSide && entity instanceof LivingEntity target) {
            if (!target.hasEffect(MobEffects.POISON)) {
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
            }
        }

    }


    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            ParticleOptions particleoptions = this.getFollowingParticle();
            if (particleoptions != null) {
                double d0 = this.getX();
                double d1 = this.getY();
                double d2 = this.getZ();

                double offsetX = (this.level().random.nextDouble() - 0.5) * 0.3;
                double offsetY = (this.level().random.nextDouble() + 0.5) * 0.3;
                double offsetZ = (this.level().random.nextDouble() - 0.5) * 0.3;

                this.level().addParticle(particleoptions, d0 + offsetX, d1 + offsetY, d2 + offsetZ, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            ParticleMethods.ParticlesBurst
                    (this.level(), getFollowingParticle(), this.getX(), this.getY() - 0.3, this.getZ(), 10, 1.5);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
