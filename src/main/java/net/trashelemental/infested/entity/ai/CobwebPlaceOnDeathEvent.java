package net.trashelemental.infested.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.entity.custom.spiders.AttackSpiderEntity;
import net.trashelemental.infested.entity.custom.spiders.SpiderMinionEntity;
import net.trashelemental.infested.infested;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber
public class CobwebPlaceOnDeathEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        BlockPos entityPos = entity.blockPosition();

        if (entity instanceof SpiderMinionEntity || entity instanceof AttackSpiderEntity) {
            placeCobwebTrap(level, entityPos);
        }
    }

    private static void placeCobwebTrap(Level level, BlockPos pos) {

        BlockState cobwebTrapState = ModBlocks.COBWEB_TRAP.get().defaultBlockState();

        if (cobwebTrapState.canSurvive(level, pos)) {
            level.setBlock(pos, cobwebTrapState, 3);
        }
    }
}
