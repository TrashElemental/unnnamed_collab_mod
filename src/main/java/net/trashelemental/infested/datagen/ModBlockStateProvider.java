package net.trashelemental.infested.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.infested;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, infested.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockWithItem(ModBlocks.CHITIN_BLOCK);
        slabBlock(((SlabBlock) ModBlocks.CHITIN_SLAB.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()));
        stairsBlock(((StairBlock) ModBlocks.CHITIN_STAIRS.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()));
        wallBlock(((WallBlock) ModBlocks.CHITIN_WALL.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()));
        BlockWithItem(ModBlocks.CHITIN_BRICKS);
        slabBlock(((SlabBlock) ModBlocks.CHITIN_BRICK_SLAB.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()));
        stairsBlock(((StairBlock) ModBlocks.CHITIN_BRICK_STAIRS.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()));
        wallBlock(((WallBlock) ModBlocks.CHITIN_BRICK_WALL.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.CHISELED_CHITIN_BRICKS.get()), blockTexture(ModBlocks.CHISELED_CHITIN_BRICKS.get()),
                new ResourceLocation(infested.MOD_ID, "block/chiseled_chitin_bricks_top"));

        blockItem(ModBlocks.CHISELED_CHITIN_BRICKS);

        BlockWithItem(ModBlocks.SILVERFISH_TRAP);
    }

    private void blockItem (RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(infested.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void BlockWithItem (RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
