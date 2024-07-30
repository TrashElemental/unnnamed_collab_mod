package net.trashelemental.infested.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.loot.AddItemModifier;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, infested.MOD_ID);
    }

    //I kinda gave up adding the items to vanilla loot tables because I swear the math wasn't working right
    //Like I know random means random but 3/100 with a 50% drop chance doesn't seem right
    //And that was over the course of several trials
    //So if you're poking around here looking for the drop chances for the Silverfish Eggs, Spider Egg, etc
    //it's in the ModEvents class
    @Override
    protected void start() {


    }
}
