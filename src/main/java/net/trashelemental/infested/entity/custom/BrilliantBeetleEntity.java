package net.trashelemental.infested.entity.custom;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.animations.ModAnimationDefinitions;
import net.trashelemental.infested.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class BrilliantBeetleEntity extends TamableAnimal {


    public BrilliantBeetleEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(BrilliantBeetleEntity.class, EntityDataSerializers.STRING);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState fallAnimationState = new AnimationState();
    private int fallAnimationTimeout = 0;


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "idle");
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            setupAnimationStates();
            updateAnimationStates();
        }

        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    private void setupAnimationStates() {
        if (!idleAnimationState.isStarted()) {
            this.idleAnimationState.start(this.tickCount);
        }

        if (this.isFalling() && !fallAnimationState.isStarted()) {
            this.fallAnimationState.start(this.tickCount);
        }
    }

    private void updateAnimationStates() {
        String currentAnimation = this.entityData.get(ANIMATION);
        if (this.isFalling()) {
            if (!currentAnimation.equals("fall")) {
                this.entityData.set(ANIMATION, "fall");
            }
        } else if (this.isWalking()) {
            if (!currentAnimation.equals("walk")) {
                this.entityData.set(ANIMATION, "walk");
            }
        } else {
            if (!currentAnimation.equals("idle")) {
                this.entityData.set(ANIMATION, "idle");
            }
        }
    }

    private boolean isFalling() {
        return !this.onGround();
    }

    private boolean isWalking() {
        return this.getDeltaMovement().lengthSqr() > 0.01;
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


    @Override
    protected void registerGoals() {
        super.registerGoals();
        {

            this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1, 10, 2, false));
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new BreedGoal(this, 1));
            this.goalSelector.addGoal(3, new FollowParentGoal(this, 1));
            this.goalSelector.addGoal(4, new PanicGoal(this, 1.2));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1, Ingredient.of(Items.COOKIE), false));
            this.goalSelector.addGoal(6, new TemptGoal(this, 1, Ingredient.of(Items.COCOA_BEANS), false));
            this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
            this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()

                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 1)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
                .add(Attributes.ATTACK_KNOCKBACK, 0);

    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.COCOA_BEANS);
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
        return ModEntities.BRILLIANT_BEETLE.get().create(serverLevel);
    }


    //Custom Behavior

    //Spider Climbing Behavior (Also some in defineSyncedData and tick.)
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(BrilliantBeetleEntity.class, EntityDataSerializers.BYTE);

    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }
        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    //Gliding Behavior
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.onGround() && this.getDeltaMovement().y < 0.0) {

            Vec3 deltaMovement = this.getDeltaMovement();
            this.setDeltaMovement(deltaMovement.multiply(1.0, 0.6, 1.0));
        }
    }

    //Does not take fall damage
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    //On right click behavior

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        //Tries to tame if the item is a cookie.
        if (itemstack.getItem() == Items.COOKIE) {
            this.usePlayerItem(pPlayer, pHand, itemstack);
            if (!this.isTame()) {
                if (this.random.nextInt(5) == 0) {
                    this.tame(pPlayer);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                this.setPersistenceRequired();
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        //Gives speed and returns a bowl if the item is Bug Stew.
        else if (itemstack.getItem() == ModItems.BUG_STEW.get() && this.isTame()) {
            if (!this.level().isClientSide) {
                if (!pPlayer.isCreative()) {
                    itemstack.shrink(1);
                    if (!pPlayer.getInventory().add(new ItemStack(Items.BOWL))) {
                        pPlayer.drop(new ItemStack(Items.BOWL), false);
                    }
                }
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1));
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
       //Start riding if it's tame.
        else if (this.isTame()) {
            pPlayer.startRiding(this);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        else {
            InteractionResult retval = super.mobInteract(pPlayer, pHand);
            if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME) {
                this.setPersistenceRequired();
            }
            return retval;
        }
        return InteractionResult.PASS;
    }

    //Riding Behavior
    @Override
    public void travel(Vec3 dir) {
        Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

        if (this.isVehicle() && entity instanceof LivingEntity passenger) {
            this.setYRot(entity.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(entity.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = entity.getYRot();
            this.yHeadRot = entity.getYRot();

            this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float forward = passenger.zza * 0.5F; //Slows it down, idk why but its speed is doubled when you're riding it
            float strafe = passenger.xxa* 0.5F; //Ditto
            super.travel(new Vec3(strafe, 0, forward));

            return;
        }
        super.travel(dir);
    }

    //Adjusts where the player visibly sits while riding
    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + -0.4;
    }


}
