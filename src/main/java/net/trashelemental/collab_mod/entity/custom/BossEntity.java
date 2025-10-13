package net.trashelemental.collab_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.trashelemental.collab_mod.CollabMod;
import org.jetbrains.annotations.Nullable;

/**
 * Template class for bosses.
 */
public class BossEntity extends PathfinderMob {

    /**
     * The NAME string is so I can be lazy and just copy-paste GeckoLib renderers.
     */
    protected String NAME;
    protected BossEvent.BossBarColor COLOR;

    @Nullable
    protected final ServerBossEvent bossEvent;

    protected boolean hasBossBar() {
        return true;
    }

    /**
     * Override this to true if you want custom names to be able to show up in the boss bar, might be worthwhile to
     * make this a config or something.
     */
    protected boolean isCustomNameAllowedInBossBar() {
        return false;
    }

    /**
     * Pass in the type and level, then in the super, assign its name (which should match its name in the registry), and
     * the color its boss bar should be. If you don't want a boss bar, override 'hasBossBar' or use the other constructor.
     */
    protected BossEntity(EntityType<? extends PathfinderMob> type, Level level, String name, BossEvent.BossBarColor color) {
        super(type, level);
        this.NAME = name;
        this.COLOR = color;

        if (level instanceof ServerLevel && hasBossBar() && color != null) {
            Component displayName;

            if (this.hasCustomName() && isCustomNameAllowedInBossBar()) {
                displayName = this.getCustomName();
            } else {
                displayName = Component.translatable("entity." + CollabMod.MOD_ID + "." + name);
            }

            bossEvent = new ServerBossEvent(
                    displayName,
                    color,
                    BossEvent.BossBarOverlay.PROGRESS
            );
            bossEvent.setVisible(true);
        } else {
            bossEvent = null;
        }
    }

    /**
     * Alternate constructor for if you don't want a boss bar.
     */
    protected BossEntity(EntityType<? extends PathfinderMob> type, Level level, String name) {
        this(type, level, name, null);
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/entity/" + NAME + ".png");
    }

    public ResourceLocation getModelLocation() {
        return new ResourceLocation(CollabMod.MOD_ID,"geo/models/entities/" + NAME + ".geo.json");
    }

    public ResourceLocation getAnimationLocation() {
        return new ResourceLocation(CollabMod.MOD_ID,"animations/" + NAME + ".animation.json");
    }



    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (hasBossBar() && bossEvent != null) bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        if (hasBossBar() && bossEvent != null) bossEvent.removePlayer(player);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            float ratio = getHealth() / getMaxHealth();
            triggerPhaseTransition(ratio);

            if (hasBossBar() && bossEvent != null) {
                bossEvent.setProgress(ratio);
            }
        }

    }


    /**
     * Lets you set the entities it should target.
     */
    protected boolean shouldTarget(LivingEntity entity) {
        return !invalidTarget(entity) && (entity instanceof Player);
    }

    /**
     * Lets you define conditions where it won't target something it would target otherwise,
     * for instance if you wanted it to ignore players with a certain potion effect or capability.
     */
    protected boolean invalidTarget(LivingEntity entity) {
        return false;
    }

    /**
     * Called when it's first spawned in naturally, if you want special VFX, animations, or something to happen.
     * Use 'onSpawnViaCommand' if you want something to only happen when it spawns via command or spawn egg.
     */
    public void onSpawnNaturally() {

    }

    /**
     * Called when it's first spawned in via command, if you want special VFX, animations, or something to happen.
     * Use 'onSpawnNaturally' if you want something to only happen when it spawns naturally.
     */
    public void onSpawnViaCommand() {

    }

    /**
     * Called when it dies, if you want special VFX, animations, advancement granting, drops, or something else to happen.
     * The killer param is the entity that caused the killing damage, or its owner if it was a tamed entity.
     */
    public void onDeath(Entity killer) {

    }

    /**
     * Allows you to define damage types that the entity should only take less damage from, for instance if you
     * wanted an entity to only take half damage from magic damage. 'getResistanceFactor' defines how resistant it is by
     * percentage, defaulting to half-damage.
     */
    public boolean resistsDamageType(DamageSource source) {
        return false;
    }

    protected float getResistanceFactor(DamageSource source) {
        return 0.5f;
    }

    /**
     * Allows you to define damage types that the entity should take no damage from, for instance if you
     * wanted an entity to be completely immune to magic damage.
     */
    public boolean immuneToDamageType(DamageSource source) {
        return false;
    }

    /**
     * Allows you to define damage types that the entity should take extra damage from, for instance if you
     * wanted an entity to only take more damage from magic damage. 'getVulnerabilityFactor' defines how weak it is by
     * percentage, defaulting to 1.5x damage.
     */
    public boolean vulnerableToDamageType(DamageSource source) {
        return false;
    }

    protected float getVulnerabilityFactor(DamageSource source) {
        return 1.5f;
    }

    /**
     * Allows you to trigger certain events that will happen once the boss reaches a certain health threshold, if you
     * wanted a boss to have a second phase or something like that.
     */
    protected void triggerPhaseTransition(float healthRatio) {}

    /**
     * Quick getter for the name of the entity, in case you want to check for custom names for a custom skin or
     * something like that.
     */
    public String getEntityName() {
        if (this.hasCustomName()) {
            return this.getCustomName().getString();
        }
        return this.getType().getDescription().getString();
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);

        if (hasBossBar() && bossEvent != null && isCustomNameAllowedInBossBar()) {
            Component displayName = (name != null)
                    ? name
                    : Component.translatable("entity." + CollabMod.MOD_ID + "." + NAME);
            bossEvent.setName(displayName);
        }
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.SPAWN_EGG || pReason == MobSpawnType.COMMAND) {
            onSpawnViaCommand();
        } else {
            onSpawnNaturally();
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void die(DamageSource pDamageSource) {

        Entity killer = pDamageSource.getEntity();
        Entity finalKiller = killer;

        if (killer instanceof TamableAnimal tamable && tamable.isTame()) {
            finalKiller = tamable.getOwner();
        }

        onDeath(finalKiller);

        super.die(pDamageSource);
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {

        float amount = pDamageAmount;

        if (resistsDamageType(pDamageSource)) {
            amount *= getResistanceFactor(pDamageSource);
        } else if (vulnerableToDamageType(pDamageSource)) {
            amount *= getVulnerabilityFactor(pDamageSource);
        }

        super.actuallyHurt(pDamageSource, amount);



    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (immuneToDamageType(pSource)) return false;

        return super.hurt(pSource, pAmount);
    }
}
