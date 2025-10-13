package net.trashelemental.collab_mod.entity.custom.bosses;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.trashelemental.collab_mod.entity.custom.BossEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class TestBossNoBar extends BossEntity implements GeoAnimatable {
    public TestBossNoBar(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level, "test_boss_no_bar");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 4.5)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();



    }

    @Override
    public void onSpawnNaturally() {

        if (!level().isClientSide) {
            System.out.println("TestBossNoBar: Boss spawned via natural means.");
        }

        super.onSpawnNaturally();
    }

    @Override
    public void onSpawnViaCommand() {

        if (!level().isClientSide) {
            System.out.println("TestBossNoBar: Boss spawned via unnatural means.");
        }

        super.onSpawnViaCommand();
    }

    @Override
    protected boolean isCustomNameAllowedInBossBar() {
        return true;
    }

    @Override
    public boolean immuneToDamageType(DamageSource source) {
        return source.is(DamageTypes.ARROW);
    }

    @Override
    public boolean resistsDamageType(DamageSource source) {
        return source.is(DamageTypes.INDIRECT_MAGIC);
    }

    @Override
    public boolean vulnerableToDamageType(DamageSource source) {
        return source.is(DamageTypes.FALLING_ANVIL);
    }



    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {

        super.actuallyHurt(pDamageSource, pDamageAmount);

        if (!level().isClientSide) {
            System.out.println("TestBossNoBar (actuallyHurt): Boss health: " + this.getHealth() + "/" + this.getMaxHealth());

            if (resistsDamageType(pDamageSource)) {
                System.out.println("Boss resisted: " + pDamageSource);
            }
            if (vulnerableToDamageType(pDamageSource)) {
                System.out.println("Boss took extra damage from: " + pDamageSource);
            }
        }
    }

    @Override
    public void onDeath(Entity killer) {

        if (!level().isClientSide) {
            System.out.println("TestBossNoBar: Boss was slain by" + killer);
        }

        super.onDeath(killer);
    }


    //Gecko
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 4, this::predicate));
    }

    private PlayState predicate(AnimationState<TestBossNoBar> state) {
        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("WALK", Animation.LoopType.LOOP));
        } else {
            state.getController().setAnimation(RawAnimation.begin().then("IDLE", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
}
