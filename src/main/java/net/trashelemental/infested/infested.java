package net.trashelemental.infested;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.client.CrimsonBeetleRenderer;
import net.trashelemental.infested.entity.client.GrubRenderer;
import net.trashelemental.infested.item.ModCreativeModeTabs;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.loot.ModLootModifiers;
import org.slf4j.Logger;

@Mod(infested.MOD_ID)
public class infested
{
    public static final String MOD_ID = "infested";

    private static final Logger LOGGER = LogUtils.getLogger();

    public infested()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
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

            EntityRenderers.register(ModEntities.CRIMSON_BEETLE.get(), CrimsonBeetleRenderer::new);
            EntityRenderers.register(ModEntities.GRUB.get(), GrubRenderer::new);

        }
    }
}
