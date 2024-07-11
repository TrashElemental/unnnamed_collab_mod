package net.trashelemental.infested.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.infested;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, infested.MOD_ID);

    public static final RegistryObject<CreativeModeTab> INFESTED_TAB = CREATIVE_MODE_TABS.register("infested_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.INSECT_TEMPLATE.get()))
                    .title(Component.translatable("creativetab.infested_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.CHITIN.get());
                        output.accept(ModItems.RAW_GRUB.get());
                        output.accept(ModItems.FRIED_GRUB.get());
                        output.accept(ModItems.BUG_STEW.get());
                        output.accept(ModItems.SILVERFISH_EGGS.get());
                        output.accept(ModItems.SPIDER_EGG.get());
                        output.accept(ModItems.INSECT_TEMPLATE.get());
                        output.accept(ModItems.SPIDER_TEMPLATE.get());

                        output.accept(ModBlocks.CHITIN_BLOCK.get());
                        output.accept(ModBlocks.CHITIN_SLAB.get());
                        output.accept(ModBlocks.CHITIN_STAIRS.get());
                        output.accept(ModBlocks.CHITIN_WALL.get());
                        output.accept(ModBlocks.CHITIN_BRICKS.get());
                        output.accept(ModBlocks.CHITIN_BRICK_SLAB.get());
                        output.accept(ModBlocks.CHITIN_BRICK_STAIRS.get());
                        output.accept(ModBlocks.CHITIN_BRICK_WALL.get());
                        output.accept(ModBlocks.CHISELED_CHITIN_BRICKS.get());

                        output.accept(ModBlocks.SILVERFISH_TRAP.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
