package net.trashelemental.infested.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, infested.MOD_ID);


    // Building Blocks
    public static final RegistryObject<Block> CHITIN_BLOCK = registerblock("chitin_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> CHITIN_BRICKS = registerblock("chitin_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));


    // Functional Blocks
    public static final RegistryObject<Block> SILVERFISH_TRAP = registerblock("silverfish_trap",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.PISTON)));


    private static <T extends Block>RegistryObject<T> registerblock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerblockitem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerblockitem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
