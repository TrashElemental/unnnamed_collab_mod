package net.trashelemental.infested.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.util.BlockEntityMapping;
import net.trashelemental.infested.util.EntitySpawnInfo;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class ModEvents {



    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event != null && event.getState() != null) {
            execute(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {

        //Jewel Beetle Spawning
        if (world instanceof ServerLevel serverLevel) {
            BlockState state = world.getBlockState(BlockPos.containing(x, y, z));
            Block block = state.getBlock();
            EntitySpawnInfo spawnInfo = BlockEntityMapping.BLOCK_ENTITY_MAP.get(block);

            if (spawnInfo != null) {
                double chance = spawnInfo.spawnChance;
                if (Math.random() < chance) {
                    Entity entity = spawnInfo.entityType.create(serverLevel);
                    if (entity != null) {
                        entity.moveTo(x + 0.5, y, z + 0.5, 0, 0);
                        serverLevel.addFreshEntity(entity);
                    }
                }
            }

            //Silverfish Eggs have a 50% chance to drop from infested variants of blocks.
            if (state.is(BlockTags.create(new ResourceLocation("infested:infested_blocks")))) {
                if (Math.random() < 0.5) {
                    ItemEntity entityToSpawn = new ItemEntity(serverLevel, x, y, z, new ItemStack(ModItems.SILVERFISH_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    serverLevel.addFreshEntity(entityToSpawn);
                }
            }
        }
    }


        //Entity Death Events
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity());
        }
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        if (entity == null)
            return;

        //Spiders have a 5% chance to drop a Spider Egg
        if (entity instanceof Spider) {
            if (Math.random() >= 0.95) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.SPIDER_EGG.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }

        //Cave Spiders have a 20% chance to drop a Spider Egg
        if (entity instanceof CaveSpider) {
            if (Math.random() >= 0.8) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.SPIDER_EGG.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }

        //Silverfish have a 10% chance to drop Silverfish Eggs
        if (entity instanceof Silverfish) {
            if (Math.random() >= 0.9) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.SILVERFISH_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }
    }




}
