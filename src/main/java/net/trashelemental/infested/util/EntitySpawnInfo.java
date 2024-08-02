package net.trashelemental.infested.util;

import net.minecraft.world.entity.EntityType;

public class EntitySpawnInfo {
    public final EntityType<?> entityType;
    public final double spawnChance;

    public EntitySpawnInfo(EntityType<?> entityType, double spawnChance) {
        this.entityType = entityType;
        this.spawnChance = spawnChance;
    }
}
