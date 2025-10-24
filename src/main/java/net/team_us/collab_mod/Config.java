package net.team_us.collab_mod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CollabMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.EnumValue<DifficultyMode> DIFFICULTY_MODE = BUILDER
            .comment("Make certain enemies and bosses have more or less challenging attack patterns and mechanics by switching to EASY or HARD. Defaults to NORMAL.")
            .defineEnum("Difficulty Mode", DifficultyMode.NORMAL);

    public enum DifficultyMode {
        EASY,
        NORMAL,
        HARD
    }

    //Honeyman
    public static final ForgeConfigSpec.IntValue HONEYMAN_REQUIRED_NAME_USAGES = BUILDER
            .comment("Control how many times the Honeyman's name must be said to summon him. Defaults to 3.")
            .defineInRange("Required mentions of the Honeyman's name to summon him:", 3, 1, 100);




    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() == Config.SPEC) {
        }

    }
}
