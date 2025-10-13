package net.trashelemental.collab_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.trashelemental.collab_mod.CollabMod;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, CollabMod.MOD_ID);
    }


    @Override
    protected void start() {


    }
}
