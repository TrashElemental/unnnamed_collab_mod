package net.trashelemental.infested.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.trashelemental.infested.infested;

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
