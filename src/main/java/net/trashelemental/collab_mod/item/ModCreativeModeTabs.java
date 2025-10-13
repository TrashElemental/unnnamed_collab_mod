package net.trashelemental.collab_mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.collab_mod.CollabMod;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CollabMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TEMPLATE_TAB = CREATIVE_MODE_TABS.register("template_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.ITEM_FRAME))
                    .title(Component.translatable("creativetab.template_tab"))
                    .displayItems((itemDisplayParameters, output) -> {



                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
