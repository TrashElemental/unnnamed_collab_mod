package net.team_us.collab_mod.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.team_us.collab_mod.Config;
import net.team_us.collab_mod.junkyard_lib.entity.MinionEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class LarvaMinionEntity extends MinionEntity implements GeoEntity {

    public LarvaMinionEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, ParticleTypes.FALLING_HONEY, SoundEvents.BEEHIVE_ENTER);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });

        this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(8, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, true));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new FloatGoal(this));
        this.goalSelector.addGoal(12, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }

    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    //Sound Events
    @Override
    public SoundEvent getAmbientSound() {
        if (this.isAggressive()) {
            return SoundEvents.BEE_LOOP_AGGRESSIVE;
        }
        return SoundEvents.BEE_LOOP;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.BEE_HURT;
    }
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }
    protected float getSoundVolume() {
        return 0.2F;
    }
    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SILVERFISH_STEP, 1F, 0.6F);
    }

    /**
     * HARD MODE: Applies hunger with attacks.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {

        if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.HARD) {
            if (entity instanceof LivingEntity target && !target.hasEffect(MobEffects.HUNGER)) {
                target.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100));
            }
        }

        return super.doHurtTarget(entity);
    }

    //GeckoLib

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 4, this::predicate));
    }

    private PlayState predicate(AnimationState<GeoAnimatable> state) {
        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("WALK", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(RawAnimation.begin().then("IDLE", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
