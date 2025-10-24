package net.team_us.collab_mod.entity.custom.bosses;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.Config;
import net.team_us.collab_mod.entity.ModEntities;
import net.team_us.collab_mod.entity.ai.KeepDistanceGoal;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import net.team_us.collab_mod.entity.custom.minions.LarvaMinionEntity;
import net.team_us.collab_mod.entity.custom.projectiles.HoneymanProjectileEntity;
import net.team_us.collab_mod.junkyard_lib.entity.MinionEntity;
import net.team_us.collab_mod.junkyard_lib.entity.method.SummonMethods;
import net.team_us.collab_mod.junkyard_lib.util.UtilMethods;
import net.team_us.collab_mod.util.DamageTypeCheckers;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

/**
 * TODO: Needs to play the hit animations when he takes damage.
 */

public class HoneymanEntity extends BossEntity implements GeoEntity {
    public HoneymanEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level, "honeyman", BossEvent.BossBarColor.YELLOW);
    }

    public enum CombatMode { MELEE, RANGED, BERSERK }

    private int SUMMON_COOLDOWN = 0;
    private int teleportInterval = 200;
    private int teleportCooldown = 0;
    private int hurtAnimCooldown = 20;
    private int berserkModeDuration = 300;
    private int berserkModeTicks = 0;
    private int MINION_CAP = 13;

    private float teleportChance = 0.4f;
    private float lockedYaw;
    private float larvaSummonChance = 0.4f;

    private boolean isSummoning = false;
    private boolean hasDoneHalfHealthTransition = false;
    private boolean hasDoneQuarterHealthTransition = false;
    private boolean isDoingPhaseTransition = false;

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 350)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 8)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 2);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class,
                10, true, false, this::shouldTarget));

        this.goalSelector.addGoal(3, new KeepDistanceGoal(this, 1.0, 10, 13) {
            @Override public boolean canUse() {return !isSummoning && getMode() == CombatMode.RANGED &&
                    getMode() != CombatMode.BERSERK && super.canUse();}});
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false) {
            @Override public boolean canUse() {return !isSummoning && getMode() != CombatMode.RANGED && super.canUse();}});

        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8) {
            @Override public boolean canUse() {return !isSummoning && super.canUse();}});
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1) {
            @Override public boolean canUse() {return !isSummoning && super.canUse();}});
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this) {
            @Override public boolean canUse() {return !isSummoning && getMode() != CombatMode.RANGED && super.canUse();}});
        this.goalSelector.addGoal(8, new FloatGoal(this));
    }

    /**
     * Adjust certain values based on difficulty.
     */
    @Override
    public void onAddedToWorld() {

        if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.EASY) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(280);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5);
            this.MINION_CAP = 8;
            this.larvaSummonChance = 0.6f;
            this.teleportChance = 0.2f;
            this.berserkModeDuration = 200;
            this.teleportInterval = 300;
        } else if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.HARD) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10);
            this.getAttribute(Attributes.ARMOR).setBaseValue(4);
            this.MINION_CAP = 18;
            this.larvaSummonChance = 0.2f;
            this.teleportChance = 0.6f;
            this.berserkModeDuration = 400;
            this.teleportInterval = 100;
            this.setHealth(this.getMaxHealth());
        }

        super.onAddedToWorld();
    }

    //Sound Events
    @Override
    public void playAmbientSound() {
        this.playSound(SoundEvents.ZOMBIE_AMBIENT, 1.0F, 0.6F);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOGLIN_STEP, 1F, 0.6F);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    /**
     * Vulnerable to Fire and Smite damage.
     * The bonus smite damage is handled in 'actuallyHurt'.
     */
    @Override
    public boolean vulnerableToDamageType(DamageSource source) {
        return DamageTypeCheckers.isFireDamage(source);
    }

    @Override
    protected float getVulnerabilityMultiplier() {
        return 1.5f;
    }

    /**
     * Immune to Poison. Becomes immune to all effects when Berserk.
     */
    @Override
    public boolean isImmuneToEffect(MobEffect effect) {
        if (getMode() == CombatMode.BERSERK) return true;
        else return effect.equals(MobEffects.POISON);
    }

    private static final EntityDataAccessor<Integer> MODE =
            SynchedEntityData.defineId(HoneymanEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MODE, CombatMode.MELEE.ordinal());
    }

    public CombatMode getMode() {
        int i = this.entityData.get(MODE);
        return CombatMode.values()[Mth.clamp(i, 0, CombatMode.values().length - 1)];
    }

    public void setMode(CombatMode mode) {
        this.entityData.set(MODE, mode.ordinal());

        if (!level().isClientSide) {
            this.getNavigation().stop();
            double speed = switch (mode) {
                case MELEE -> 0.35;
                case RANGED -> 0.25;
                case BERSERK -> 0.4;
            };
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
        }
    }

    @Override
    public void tick() {
        if (hurtAnimCooldown > 0) hurtAnimCooldown--;
        if (teleportCooldown > 0) teleportCooldown--;
        if (SUMMON_COOLDOWN > 0) SUMMON_COOLDOWN--;
        LivingEntity target = getTarget();

        if (!level().isClientSide) {
            if (isSummoning) {
                setDeltaMovement(Vec3.ZERO);
                getNavigation().stop();
                setYRot(lockedYaw);
                setYHeadRot(lockedYaw);
                setYBodyRot(lockedYaw);
            }

            if (getMode() == CombatMode.RANGED && SUMMON_COOLDOWN <= 0 && !isDoingPhaseTransition) {
                if (tooManyMinions()) {
                    performRangedAttack();
                } else {
                    performSummonAttack();
                }
                SUMMON_COOLDOWN = 150 + random.nextInt(100);
            }

            if (getMode() == CombatMode.BERSERK) {
                if (berserkModeTicks > 0) {
                    berserkModeTicks--;
                } else {
                    setMode(CombatMode.MELEE);
                    level().playSound(null, blockPosition(),
                            SoundEvents.ZOMBIFIED_PIGLIN_DEATH, SoundSource.HOSTILE, 5.0F, 1F);
                }
            }

            if (target != null && getMode() == CombatMode.MELEE && getMode() != CombatMode.BERSERK) {
                double distanceSq = this.distanceToSqr(target);
                if (distanceSq > 7 * 7) {
                    setMode(CombatMode.RANGED);
                }
            }
        }

        super.tick();
    }

    @Override
    protected void triggerPhaseTransition(float healthRatio) {
        LivingEntity target = getTarget();
        if (target == null || this.level().isClientSide) return;
        if (this.isSummoning) return;

        //Triggers at 1/2 health
        if (!hasDoneHalfHealthTransition && healthRatio <= 0.5f) {
            hasDoneHalfHealthTransition = true;
            berserkPhaseTransition(target);
            this.removeAllEffects();
        }

        //Triggers at 1/4 health.
        if (!hasDoneQuarterHealthTransition && healthRatio <= 0.25f) {
            hasDoneQuarterHealthTransition = true;
            berserkPhaseTransition(target);
            this.removeAllEffects();
        }
    }


    @Override
    protected void actuallyHurt(DamageSource source, float amount) {
        Entity attacker = source.getEntity();

        if (!this.level().isClientSide) {
            if (attacker instanceof LivingEntity entity && !(entity instanceof Player player && player.isCreative())) {
                double distSq = this.distanceToSqr(entity);

                //If the attacker is closer than four blocks, and boss is currently in ranged mode, switch to melee
                if (distSq <= 4 * 4) {
                    if (getMode() == CombatMode.RANGED) {
                        setMode(CombatMode.MELEE);
                    }
                }
                //If the attacker is more than 7 blocks away and the boss is not in berserk mode, switch to ranged from melee if applicable
                else if (distSq > 7 * 7 && getMode() != CombatMode.BERSERK) {
                    if (getMode() == CombatMode.MELEE) setMode(CombatMode.RANGED);
                }

                //Try to teleport
                if (teleportCooldown <= 0 && this.random.nextFloat() < teleportChance && !this.isSummoning) {
                    BlockPos preTeleportPos = this.blockPosition();
                    teleport(entity);

                    //HARD MODE: Up to 8
                    //EASY MODE: Always 3
                    int count;

                    if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.HARD) {
                        count = 3 + this.random.nextInt(6);
                    } else if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.EASY) {
                        count = 3;
                    } else {
                        count = 3 + this.random.nextInt(3);
                    }

                    for (int i = 0; i < count; i++) {
                        boolean summonLarva = this.random.nextFloat() < larvaSummonChance;

                        MinionEntity minion = summonLarva
                                ? new LarvaMinionEntity(ModEntities.HONEYMAN_LARVA_MINION.get(), this.level())
                                : new BeeMinionEntity(ModEntities.HONEYMAN_BEE_MINION.get(), this.level());

                        int offsetX = (int) ((this.random.nextDouble() - 0.5D) * 6.0D);
                        int offsetZ = (int) ((this.random.nextDouble() - 0.5D) * 6.0D);
                        BlockPos spawnPos = preTeleportPos.offset(offsetX, 0, offsetZ);

                        SummonMethods.summonMinion(this.level(), spawnPos, minion, 600, false, null);
                        minion.setTarget(entity);
                    }
                }
            }
        }

        //if attacker's weapon was enchanted with smite, add 2 to the damage amount for each smite level.
        if (attacker instanceof LivingEntity livingAttacker) {
            int smiteLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, livingAttacker.getMainHandItem());
            if (smiteLevel > 0) {
                amount += smiteLevel * 2.0F;
            }
        }

        super.actuallyHurt(source, amount);
    }

    private void teleport(Entity attacker) {
        Level level = this.level();
        if (level.isClientSide) return;

        for (int attempt = 0; attempt < 10; attempt++) {
            double angle = this.random.nextDouble() * 2 * Math.PI;
            double distance = 10 + this.random.nextDouble() * 10;
            double offsetX = Math.cos(angle) * distance;
            double offsetZ = Math.sin(angle) * distance;

            double targetX = this.getX() + offsetX;
            double targetY = this.getY();
            double targetZ = this.getZ() + offsetZ;

            BlockPos pos = BlockPos.containing(targetX, targetY, targetZ);

            while (pos.getY() > level.getMinBuildHeight() && !level.getBlockState(pos.below()).blocksMotion()) {
                pos = pos.below();
            }

            AABB boundingBox = this.getBoundingBox().move(targetX - this.getX(), 0, targetZ - this.getZ());
            if (level.noCollision(this, boundingBox) && !level.containsAnyLiquid(boundingBox)) {
                this.setPos(targetX, pos.getY(), targetZ);
                this.setDeltaMovement(0, 0, 0);
                this.getNavigation().stop();


                if (attacker instanceof LivingEntity entity && (!(entity instanceof Player player && player.isCreative()))) {
                    entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
                    level().playSound(null, blockPosition(),
                            SoundEvents.EVOKER_CAST_SPELL, SoundSource.HOSTILE, 2.0F, 1F);
                }

                if (getMode() == CombatMode.BERSERK) {
                    teleportCooldown = 40 + this.random.nextInt(50);
                } else teleportCooldown = 200 + this.random.nextInt(50);

                return;
            }
        }
    }

    public void performSummonAttack() {
        if (this.isDeadOrDying()) return;

        if (!this.level().isClientSide) {
            this.triggerAnim("summonController", "summon");

            this.isSummoning = true;
            this.lockedYaw = this.getYRot();

            //HARD MODE: Up to 4
            //EASY MODE: Always 1
            int minionsToSummon;

            if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.HARD) {
                minionsToSummon = 1 + this.random.nextInt(4);
            } else if (Config.DIFFICULTY_MODE.get() == Config.DifficultyMode.EASY) {
                minionsToSummon = 1;
            } else {
                minionsToSummon = 1 + this.random.nextInt(2);
            }

            CollabMod.queueServerWork(16, () -> {
                //Sound for putting first arm on the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            CollabMod.queueServerWork(30, () -> {
                //Sound for putting second arm on the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            //Thought it would be cool if he was vulnerable while he had his chest exposed, like it's a weak point
            //Can take it out if we don't like it.
            CollabMod.queueServerWork(58, () -> {
                this.setVulnerable(40);

                //Sound for fully opening the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.6F);
            });


            CollabMod.queueServerWork(70, () -> {
                summonMinions(minionsToSummon, 0.4f);

                //Sound for him shaking.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.HOSTILE, 8.0F, 0.6F);
            });
            CollabMod.queueServerWork(76, () -> {
                summonMinions(minionsToSummon, 0.4f);

                //Sound for him shaking.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.HOSTILE, 8.0F, 0.6F);
            });
            CollabMod.queueServerWork(86, () -> {
                summonMinions(minionsToSummon, 0.4f);

                //Sound for him shaking.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.HOSTILE, 8.0F, 0.6F);
            });


            CollabMod.queueServerWork(100, () -> {
                //Sound for closing first half of the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            CollabMod.queueServerWork(105, () -> {
                //Sound for closing second half of the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            CollabMod.queueServerWork(128, () -> {
                this.isSummoning = false;
            });

        }
    }

    private void summonMinions(int count, float ejectionForce) {
        if (this.isDeadOrDying()) return;
        if (this.level().isClientSide) return;
        LivingEntity target = this.getTarget();
        Vec3 lookVec = this.getLookAngle().normalize();

        for (int i = 0; i < count; i++) {
            BlockPos pos = getSummonPosition();

            boolean summonLarva = this.random.nextFloat() < larvaSummonChance;

            MinionEntity minion = summonLarva
                    ? new LarvaMinionEntity(ModEntities.HONEYMAN_LARVA_MINION.get(), this.level())
                    : new BeeMinionEntity(ModEntities.HONEYMAN_BEE_MINION.get(), this.level());

            SummonMethods.summonMinion(this.level(), pos, minion, 600, false, null);

            float yaw = this.getYRot();
            minion.setYRot(yaw);
            minion.yRotO = yaw;

            if (ejectionForce > 0f) {
                double xMotion = lookVec.x * ejectionForce + (this.random.nextDouble() - 0.5) * 0.2;
                double yMotion = this.random.nextDouble() * 0.1;
                double zMotion = lookVec.z * ejectionForce + (this.random.nextDouble() - 0.5) * 0.2;
                minion.setDeltaMovement(xMotion, yMotion, zMotion);
            }

            if (target != null) minion.setTarget(target);
        }

        //Sound for ejecting the minions.
        level().playSound(null, blockPosition(),
                SoundEvents.BEEHIVE_EXIT, SoundSource.HOSTILE, 5.0F, 0.6F);
    }

    private boolean tooManyMinions() {
        int beeCount = this.level().getEntitiesOfClass(BeeMinionEntity.class,
                this.getBoundingBox().inflate(32.0D), LivingEntity::isAlive).size();

        int larvaCount = this.level().getEntitiesOfClass(LarvaMinionEntity.class,
                this.getBoundingBox().inflate(32.0D), LivingEntity::isAlive).size();

        return (beeCount + larvaCount) >= MINION_CAP;
    }

    /**
     * Ranged attack that he can use in the event that there are too many minions around.
     */
    public void performRangedAttack() {
        if (this.isDeadOrDying()) return;

        if (!this.level().isClientSide) {
            this.triggerAnim("summonController", "summon");

            this.isSummoning = true;
            this.lockedYaw = this.getYRot();

            CollabMod.queueServerWork(16, () -> {

                //Sound for putting first arm on the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            CollabMod.queueServerWork(30, () -> {

                //Sound for putting second arm on the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            //Thought it would be cool if he was vulnerable while he had his chest exposed, like it's a weak point
            //Can take it out if we don't like it.
            CollabMod.queueServerWork(58, () -> {
                this.setVulnerable(40);

                //Sound for fully opening the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.6F);
            });


            CollabMod.queueServerWork(70, () -> {
                fireProjectileAtTarget(this.getTarget());

                //Sound for him shaking.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.HOSTILE, 8.0F, 0.6F);
            });
            CollabMod.queueServerWork(76, () -> {
                fireProjectileAtTarget(this.getTarget());

                //Sound for him shaking.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.HOSTILE, 8.0F, 0.6F);
            });
            CollabMod.queueServerWork(86, () -> {
                fireProjectileAtTarget(this.getTarget());

                //Sound for him shaking.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_TURTLE, SoundSource.HOSTILE, 8.0F, 0.6F);
            });


            CollabMod.queueServerWork(100, () -> {
                //Sound for closing first half of the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            CollabMod.queueServerWork(105, () -> {
                //Sound for closing second half of the coat.
                level().playSound(null, blockPosition(),
                        SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 8.0F, 0.8F);
            });

            CollabMod.queueServerWork(128, () -> {
                this.isSummoning = false;
            });

        }
    }

    public void fireProjectileAtTarget(LivingEntity target) {
        if (target == null) return;

        int amount = 1 + this.random.nextInt(3);
        for (int i = 0; i < amount; i++) {
            int delay = i * 5;
            CollabMod.queueServerWork(delay, () -> {
                BlockPos pos = getSummonPosition();
                HoneymanProjectileEntity proj = new HoneymanProjectileEntity(this, this.level());
                proj.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

                double dx = target.getX() - proj.getX();
                double dy = target.getEyeY() - proj.getY();
                double dz = target.getZ() - proj.getZ();

                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist < 0.001) dist = 0.001;

                dx /= dist;
                dz /= dist;

                double vy = dy / Math.max(1.0, dist) + 0.2;
                double force = 0.6 + random.nextDouble() * 0.2;

                proj.shoot(dx, vy, dz, (float) force, 0.3F);
                this.level().addFreshEntity(proj);
                level().playSound(null, blockPosition(),
                        SoundEvents.FOX_SPIT, SoundSource.HOSTILE, 5.0F, 1F);
            });
        }
    }

    private BlockPos getSummonPosition() {
        Vec3 lookVec = this.getLookAngle().normalize();

        double spreadX = (this.random.nextDouble() - 0.5) * 0.5;
        double spreadZ = (this.random.nextDouble() - 0.5) * 0.5;
        double spreadY = (this.random.nextDouble() - 0.5) * 0.4;

        double spawnX = this.getX() + lookVec.x + spreadX;
        double spawnY = this.getY() + 1.2 + spreadY;
        double spawnZ = this.getZ() + lookVec.z + spreadZ;

        return BlockPos.containing(spawnX, spawnY, spawnZ);
    }

    /**
     * At half health, and then again at 25% health, he will teleport away, summon 6-9 minions, and enter 'berserk' mode,
     * during which he chases down the player much faster and won't teleport away or enter ranged mode if they get too
     * far away.
     */
    public void berserkPhaseTransition(LivingEntity target) {
        if (this.level().isClientSide) return;

        this.isDoingPhaseTransition = true;

        //First, teleport.
        teleport(target);

        //Sound to indicate phase transition is starting.
        level().playSound(null, blockPosition(),
                SoundEvents.EVOKER_PREPARE_ATTACK, SoundSource.HOSTILE, 5.0F, 1F);

        //Give hyper armor for the duration of the summon animation.
        setHyperArmor(125);

        //Start the summon animation and summon 2-3 minions each time
        this.triggerAnim("summonController", "summon");
        this.lockedYaw = this.getYRot();

        CollabMod.queueServerWork(70, () -> {
            summonMinions(2, 0.4f);
        });
        CollabMod.queueServerWork(76, () -> {
            summonMinions(3, 0.4f);
        });
        CollabMod.queueServerWork(86, () -> {
            summonMinions(2, 0.4f);
        });

        //Go Berserk once the summon animation is done.
        CollabMod.queueServerWork(128, () -> {
            setMode(CombatMode.BERSERK);
            this.berserkModeTicks = berserkModeDuration;
            this.isDoingPhaseTransition = false;

            //Sound to indicate he's berserk now.
            level().playSound(null, blockPosition(),
                    SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, SoundSource.HOSTILE, 5.0F, 1F);
        });

    }


    @Override
    public void onDeath(Entity killer) {

        if (killer instanceof ServerPlayer player) {
            UtilMethods.grantAdvancement(player, "kill_honeyman");
        }

        super.onDeath(killer);
    }

    //Animation stuff
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 8, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController", 8, this::attackPredicate));

        controllers.add(new AnimationController<>(this, "summonController", 0, state -> PlayState.STOP)
                .triggerableAnim("summon", RawAnimation.begin().then("summon", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "light_hitController", 0, state -> PlayState.STOP)
                .triggerableAnim("light_hit", RawAnimation.begin().then("hit2", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "heavy_hitController", 0, state -> PlayState.STOP)
                .triggerableAnim("heavy_hit", RawAnimation.begin().then("hit", Animation.LoopType.PLAY_ONCE)));
        controllers.add(new AnimationController<>(this, "deathController", 0, state -> PlayState.STOP)
                .triggerableAnim("death", RawAnimation.begin().then("death", Animation.LoopType.PLAY_ONCE)));
    }

    private PlayState predicate(AnimationState<HoneymanEntity> state) {

        if (state.isMoving()) {
            if (getMode() != CombatMode.RANGED) {
                state.getController().setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
            }
            else state.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
        } else {
            state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }

        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationState<GeoAnimatable> state) {
        if (this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            String attackAnim = "attack";
            state.getController().setAnimation(RawAnimation.begin().then(attackAnim, Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /**
     * Delaying certain events to line up with animations and other animation logic.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        CollabMod.queueServerWork(10, () -> {
            super.doHurtTarget(entity);
        });
        return false;
    }

    @Override
    public void die(DamageSource pDamageSource) {
        this.setNoAi(true);
        if (!this.level().isClientSide) {
            this.triggerAnim("deathController", "death");
        }

        //Thump sound when he hits the ground.
        CollabMod.queueServerWork(45, () -> {
            level().playSound(null, blockPosition(),
                    SoundEvents.POLAR_BEAR_STEP, SoundSource.HOSTILE, 10F, 0.5F);
        });

        CollabMod.queueServerWork(56, () -> {
            super.die(pDamageSource);
        });
    }

}
