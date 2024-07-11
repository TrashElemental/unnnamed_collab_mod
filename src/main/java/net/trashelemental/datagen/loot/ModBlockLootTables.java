package net.trashelemental.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.block.ModBlocks;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CHITIN_BLOCK.get());
        this.add(ModBlocks.CHITIN_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.CHITIN_SLAB.get()));
        this.dropSelf(ModBlocks.CHITIN_STAIRS.get());
        this.dropSelf(ModBlocks.CHITIN_WALL.get());
        this.dropSelf(ModBlocks.CHITIN_BRICKS.get());
        this.add(ModBlocks.CHITIN_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.CHITIN_BRICK_SLAB.get()));
        this.dropSelf(ModBlocks.CHITIN_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.CHITIN_BRICK_WALL.get());
        this.dropSelf(ModBlocks.CHISELED_CHITIN_BRICKS.get());

        this.dropSelf(ModBlocks.SILVERFISH_TRAP.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
