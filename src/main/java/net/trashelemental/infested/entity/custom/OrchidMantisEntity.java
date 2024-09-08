package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.ai.MantisAttackGoal;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OrchidMantisEntity extends TamableAnimal {


    public OrchidMantisEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(OrchidMantisEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    //public final AnimationState fallAnimationState = new AnimationState();
    //private int fallAnimationTimeout = 0;


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 8;
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        } else {
            f = 0f;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        {
            this.goalSelector.addGoal(0, new OwnerHurtByTargetGoal(this) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(OrchidMantisEntity.this);
                }
            });
            this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(OrchidMantisEntity.this);
                }
            });
            this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(3, new MantisAttackGoal<>(
                    this, LivingEntity.class, true, (entity) -> entity.getType().is(PREY))
            {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(OrchidMantisEntity.this);
                }
            });
            this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false) {
                @Override
                protected double getAttackReachSqr(LivingEntity entity) {
                    return 4;
                }

                @Override
                public void start() {
                    super.start();
                    setAttacking(true);
                }

                @Override
                public void stop() {
                    setAttacking(false);
                    super.stop();
                }
            });
            this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 10, 2, false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(OrchidMantisEntity.this);
                }
            });
            this.goalSelector.addGoal(6, new FloatGoal(this));
            this.goalSelector.addGoal(7, new BreedGoal(this, 1));
            this.goalSelector.addGoal(8, new FollowParentGoal(this, 1));
            this.goalSelector.addGoal(9, new TemptGoal(this, 1, Ingredient.of(Items.SPIDER_EYE), false));
            this.goalSelector.addGoal(10, new TemptGoal(this, 1, Ingredient.of(Items.FERMENTED_SPIDER_EYE), false));
            this.goalSelector.addGoal(11, new RandomStrollGoal(this, 1) {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(OrchidMantisEntity.this);
                }
            });
            this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
        }
    }

    public static boolean follow(OrchidMantisEntity entity) {
        if (entity == null)
            return false;
        return entity.isFollowing();
    }

    public static boolean wander(OrchidMantisEntity entity) {
        if (entity == null)
            return false;
        return entity.isWandering();
    }

    private static final TagKey<EntityType<?>> PREY = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("infested:mantis_prey"));

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()

                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
                .add(Attributes.ATTACK_KNOCKBACK, 0);

    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.FERMENTED_SPIDER_EYE);
    }

    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }


    //Sound Events
    @Override
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.puffer_fish.ambient"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.step"))), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.puffer_fish.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.puffer_fish.death"));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.ORCHID_MANTIS.get().create(serverLevel);
    }

    //Spawning

public static boolean canSpawn(EntityType<OrchidMantisEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
}



    //Custom Behavior

    //On right click behavior
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        //Tries to tame if the item is a Spider Eye.
        if (itemstack.getItem() == Items.SPIDER_EYE) {
            this.usePlayerItem(pPlayer, pHand, itemstack);
            if (!this.isTame()) {
                if (this.random.nextInt(5) == 0) {
                    this.tame(pPlayer);
                    this.BEHAVIOR = "FOLLOW";
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                this.setPersistenceRequired();
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        } else {
            InteractionResult retval = super.mobInteract(pPlayer, pHand);
            if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME) {
                this.setPersistenceRequired();
            }

            if (this.isOwnedBy(pPlayer)) {
                cycleBehavior(pPlayer);
            }

            return retval;
        }
        return InteractionResult.PASS;
    }

    //Behavior
    private String BEHAVIOR = "WANDER";

    private void setBehaviorInPersistentData(String behavior) {
        CompoundTag tag = this.getPersistentData();
        tag.putString("Behavior", behavior);
    }

    public boolean isFollowing() {
        return this.BEHAVIOR.equals("FOLLOW");
    }

    public boolean isWandering() {
        return this.BEHAVIOR.equals("WANDER");
    }

    private void cycleBehavior(Player pPlayer) {
        switch (this.BEHAVIOR) {
            case "FOLLOW":
                this.BEHAVIOR = "WANDER";
                pPlayer.displayClientMessage(Component.literal("Mantis will wander"), true);
                break;
            case "STAY":
                this.BEHAVIOR = "FOLLOW";
                pPlayer.displayClientMessage(Component.literal("Mantis will follow"), true);
                break;
            case "WANDER":
                this.BEHAVIOR = "STAY";
                pPlayer.displayClientMessage(Component.literal("Mantis will stay"), true);
                break;
        }
        this.setBehaviorInPersistentData(this.BEHAVIOR);
    }

    //NBT Tags
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Behavior", this.BEHAVIOR);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Behavior")) {
            this.BEHAVIOR = compound.getString("Behavior");
        }
    }

    //Gliding Behavior
    //Leaving this here because I might eventually do a remodel and add wings to the mantis.
    //If I do I'll let it slow fall the same way the B. Beetle does.
    //Mantises can fly they're just bad at it
    //@Override
   // public void aiStep() {
    //    super.aiStep();

    //    if (!this.onGround() && this.getDeltaMovement().y < 0.0) {

    //        Vec3 deltaMovement = this.getDeltaMovement();
    //        this.setDeltaMovement(deltaMovement.multiply(1.0, 0.6, 1.0));
    //    }
    //}

    //Does not take fall damage
    //See above
   // @Override
    //public boolean hurt(DamageSource source, float amount) {
   //     if (source.is(DamageTypes.FALL))
   //         return false;
   //     return super.hurt(source, amount);
   // }

}
