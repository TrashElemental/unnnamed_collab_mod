package net.trashelemental.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, infested.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.CHITIN);
        simpleItem(ModItems.INSECT_TEMPLATE);
        simpleItem(ModItems.SPIDER_TEMPLATE);

        simpleItem(ModItems.RAW_GRUB);
        simpleItem(ModItems.FRIED_GRUB);
        simpleItem(ModItems.BUG_STEW);

        simpleItem(ModItems.SILVERFISH_EGGS);
        simpleItem(ModItems.SPIDER_EGG);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(infested.MOD_ID, "item/" + item.getId().getPath()));
    }
}
