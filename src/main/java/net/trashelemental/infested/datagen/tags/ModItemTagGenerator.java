package net.trashelemental.infested.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, infested.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        addForgeTags();
        addNeoforgeTags();

    }

    private void addForgeTags() {

        //Armor sets
        //Spider Set
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/helmets"))).add(ModItems.SPIDER_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/chestplates"))).add(ModItems.SPIDER_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/chest_armor"))).add(ModItems.SPIDER_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/leggings"))).add(ModItems.SPIDER_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/boots"))).add(ModItems.SPIDER_BOOTS.get());
        //Chitin Set
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/helmets"))).add(ModItems.CHITIN_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/chestplates"))).add(ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/chest_armor"))).add(ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/leggings"))).add(ModItems.CHITIN_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "items/armor/boots"))).add(ModItems.CHITIN_BOOTS.get());
    }

    private void addNeoforgeTags() {

        //Armor sets
        tag(ItemTags.create(new ResourceLocation("neoforge", "items/armor/helmets"))).add(ModItems.SPIDER_HELMET.get(), ModItems.CHITIN_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "items/armor/chestplates"))).add(ModItems.SPIDER_CHESTPLATE.get(), ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "items/armor/chest_armor"))).add(ModItems.SPIDER_CHESTPLATE.get(), ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "items/armor/leggings"))).add(ModItems.SPIDER_LEGGINGS.get(), ModItems.CHITIN_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "items/armor/boots"))).add(ModItems.SPIDER_BOOTS.get(), ModItems.CHITIN_BOOTS.get());
    }
}
