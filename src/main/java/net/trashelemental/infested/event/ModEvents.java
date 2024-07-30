package net.trashelemental.infested.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class ModEvents {



    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    //Silverfish Eggs have a 50% chance to drop from Infested Blocks
    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
        if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(new ResourceLocation("infested:infested_blocks")))) {
            if (Math.random() >= 0.5) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z,
                            new ItemStack(ModItems.SILVERFISH_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity());
        }
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(null, world, entity);
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
