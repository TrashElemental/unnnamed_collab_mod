package net.team_us.collab_mod.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.team_us.collab_mod.CollabMod;

import java.util.function.Consumer;

public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {

        String namespace = CollabMod.MOD_ID;

        Advancement SayHoneyman = Advancement.Builder.advancement()
                .display(
                        Items.SPONGE,
                        Component.translatable("advancements.mod_name.say_honeyman.title"),
                        Component.translatable("advancements.mod_name.say_honeyman.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.GOAL,
                        true,  // show toast
                        true,  // announce to chat
                        true  // hidden
                )
                .parent(new ResourceLocation("minecraft:adventure/root"))
                .addCriterion("impossible", new ImpossibleTrigger.TriggerInstance())
                .save(writer, new ResourceLocation(namespace,"say_honeyman"), existingFileHelper);

        Advancement SummonHoneyman = Advancement.Builder.advancement()
                .display(
                        Items.SPONGE,
                        Component.translatable("advancements.mod_name.summon_honeyman.title"),
                        Component.translatable("advancements.mod_name.summon_honeyman.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.GOAL,
                        true,  // show toast
                        true,  // announce to chat
                        true  // hidden
                )
                .parent(SayHoneyman)
                .addCriterion("impossible", new ImpossibleTrigger.TriggerInstance())
                .save(writer, new ResourceLocation(namespace,"summon_honeyman"), existingFileHelper);

        Advancement KillHoneyman = Advancement.Builder.advancement()
                .display(
                        Items.SPONGE,
                        Component.translatable("advancements.mod_name.kill_honeyman.title"),
                        Component.translatable("advancements.mod_name.kill_honeyman.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.CHALLENGE,
                        true,  // show toast
                        true,  // announce to chat
                        true  // hidden
                )
                .parent(SummonHoneyman)
                .addCriterion("impossible", new ImpossibleTrigger.TriggerInstance())
                .save(writer, new ResourceLocation(namespace,"kill_honeyman"), existingFileHelper);

    }
}
