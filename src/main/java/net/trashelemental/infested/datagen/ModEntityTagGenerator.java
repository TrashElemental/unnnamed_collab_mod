package net.trashelemental.infested.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.infested;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagGenerator extends EntityTypeTagsProvider {

    public ModEntityTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, infested.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagKey<EntityType<?>> mantisPreyTag = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("infested:mantis_prey"));

        this.tag(mantisPreyTag).add(
                EntityType.SPIDER,
                EntityType.CAVE_SPIDER,
                EntityType.SILVERFISH,
                EntityType.ENDERMITE,
                ModEntities.GRUB.get(),
                ModEntities.CRIMSON_BEETLE.get(),
                ModEntities.ANCIENT_DEBREETLE.get(),
                ModEntities.CHORUS_BEETLE.get(),
                ModEntities.HARVEST_BEETLE.get(),
                ModEntities.JEWEL_BEETLE.get(),
                ModEntities.BRILLIANT_BEETLE.get()
        );
    }
}
