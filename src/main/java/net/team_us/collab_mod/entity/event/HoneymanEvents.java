package net.team_us.collab_mod.entity.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_us.collab_mod.Config;
import net.team_us.collab_mod.entity.ModEntities;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;
import net.team_us.collab_mod.junkyard_lib.entity.method.SummonMethods;
import net.team_us.collab_mod.junkyard_lib.util.UtilMethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class HoneymanEvents {

    private static final Map<UUID, Integer> counter = new HashMap<>();

    @SubscribeEvent
    public static void ListenForHoneyman(ServerChatEvent event) {
        String message = String.valueOf(event.getMessage());
        ServerPlayer player = event.getPlayer();
        UUID playerId = player.getUUID();

        //Made this a config just in case anyone wants to change it for some reason.
        int requiredNameUsages = Config.HONEYMAN_REQUIRED_NAME_USAGES.get();

        int matches = countHoneymanMentions(message);
        if (matches > 0) {
            int count = counter.getOrDefault(playerId, 0) + matches;
            counter.put(playerId, count);

            UtilMethods.grantAdvancement(player, "say_honeyman");

            if (count >= requiredNameUsages) {
                counter.put(playerId, 0);
                SummonHoneyman(player);
            }
        }
    }


    public static void SummonHoneyman(ServerPlayer player) {
        if (player.level().isClientSide) return;

        Level level = player.level();

        List<HoneymanEntity> existing = level.getEntitiesOfClass(
                HoneymanEntity.class, player.getBoundingBox().inflate(64.0));
        if (!existing.isEmpty()) { return; }

        UtilMethods.grantAdvancement(player, "summon_honeyman");

        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false));
        level.playSound(null, player.blockPosition(),
                SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 5.0F, 1F);

        BlockPos playerPos = player.blockPosition();
        BlockPos summonPos = null;

        for (int i = 0; i < 20; i++) {
            double angle = player.getRandom().nextDouble() * 2 * Math.PI;
            double distance = 5 + player.getRandom().nextDouble() * 10;
            int offsetX = (int) (Math.cos(angle) * distance);
            int offsetZ = (int) (Math.sin(angle) * distance);

            BlockPos candidatePos = playerPos.offset(offsetX, 0, offsetZ);

            candidatePos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, candidatePos);

            while (!level.getBlockState(candidatePos.below()).blocksMotion()) {
                candidatePos = candidatePos.below();
                if (candidatePos.getY() <= level.getMinBuildHeight()) break;
            }

            AABB boundingBox = ModEntities.HONEYMAN.get().getAABB(
                    candidatePos.getX() + 0.5, candidatePos.getY(), candidatePos.getZ() + 0.5);
            if (level.noCollision(null, boundingBox) && !level.containsAnyLiquid(boundingBox)) {
                summonPos = candidatePos;
                break;
            }
        }

        if (summonPos == null) summonPos = playerPos;

        HoneymanEntity honeyman = new HoneymanEntity(ModEntities.HONEYMAN.get(), level);
        honeyman.moveTo(summonPos.getX() + 0.5, summonPos.getY() + 0.1, summonPos.getZ() + 0.5, 0, 0);
        SummonMethods.summonEntity(level, summonPos, honeyman);
        if (!player.isCreative()) honeyman.setTarget(player);
    }



    //This can be expanded later if we want to add other lang entries
    //Or jokey stuff like 'Beetlejuice' or 'Candlejack'.
    private static final Pattern HONEYMAN_PATTERN = Pattern.compile(
            "(?i)(honeyman|honey[- ]?man)"
    );

    public static int countHoneymanMentions(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }

        int count = 0;
        Matcher matcher = HONEYMAN_PATTERN.matcher(string);
        while (matcher.find()) {
            count++;
        }
        return count;
    }


}
