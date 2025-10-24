package net.team_us.collab_mod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.team_us.collab_mod.block.ModBlocks;
import net.team_us.collab_mod.entity.ModEntities;
import net.team_us.collab_mod.entity.client.renderers.BeeMinionRenderer;
import net.team_us.collab_mod.entity.client.renderers.HoneymanProjectileRenderer;
import net.team_us.collab_mod.entity.client.renderers.HoneymanRenderer;
import net.team_us.collab_mod.entity.client.renderers.LarvaMinionRenderer;
import net.team_us.collab_mod.entity.custom.minions.LarvaMinionEntity;
import net.team_us.collab_mod.item.ModCreativeModeTabs;
import net.team_us.collab_mod.item.ModItems;
import net.team_us.collab_mod.magic.brewing.ModPotions;
import net.team_us.collab_mod.magic.effects.ModMobEffects;
import net.team_us.collab_mod.magic.enchantments.ModEnchantments;
import org.slf4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(CollabMod.MOD_ID)
public class CollabMod
{
    public static final String MOD_ID = "collab_mod";

    private static final Logger LOGGER = LogUtils.getLogger();

    public CollabMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModPotions.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModEnchantments.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
    
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register(ModEntities.HONEYMAN.get(), HoneymanRenderer::new);
            EntityRenderers.register(ModEntities.HONEYMAN_BEE_MINION.get(), BeeMinionRenderer::new);
            EntityRenderers.register(ModEntities.HONEYMAN_LARVA_MINION.get(), LarvaMinionRenderer::new);
            EntityRenderers.register(ModEntities.HONEYMAN_PROJECTILE_ENTITY.get(), HoneymanProjectileRenderer::new);

        }
    }


    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tickDelay, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry<>(action, tickDelay));
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actionsToRun = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() <= 0) {
                    actionsToRun.add(work);
                }
            });
            actionsToRun.forEach(work -> work.getKey().run());
            workQueue.removeAll(actionsToRun);
        }
    }

}
