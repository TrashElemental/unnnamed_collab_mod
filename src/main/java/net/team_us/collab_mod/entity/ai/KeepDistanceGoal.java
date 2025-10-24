package net.team_us.collab_mod.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;

import java.util.EnumSet;

public class KeepDistanceGoal extends Goal {
    private final Mob mob;
    private final double moveSpeed;
    private final float minDistance;
    private final float maxDistance;

    private int seeTime = 0;
    private int strafingTime = -1;
    private boolean strafingClockwise = false;
    private boolean strafingBackwards = false;

    public KeepDistanceGoal(Mob mob, double moveSpeed, float minDistance, float maxDistance) {
        this.mob = mob;
        this.moveSpeed = moveSpeed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) return false;
        if (mob instanceof HoneymanEntity honeyman)
            return honeyman.getMode() == HoneymanEntity.CombatMode.RANGED;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) return false;
        if (mob instanceof HoneymanEntity honeyman)
            return honeyman.getMode() == HoneymanEntity.CombatMode.RANGED;
        return !mob.getNavigation().isDone() || canUse();
    }

    @Override
    public void start() {
        this.seeTime = 0;
        this.strafingTime = -1;
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
        this.strafingTime = -1;
        mob.getMoveControl().setWantedPosition(mob.getX(), mob.getY(), mob.getZ(), 0);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {

        LivingEntity target = mob.getTarget();
        if (target == null) return;

        if (!canUse()) {
            mob.getNavigation().stop();
            mob.getMoveControl().strafe(0, 0);
            mob.lookAt(target, 30.0F, 30.0F);
            this.strafingTime = -1;
            return;
        }

        double distSq = mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean canSee = mob.getSensing().hasLineOfSight(target);

        boolean hadLine = this.seeTime > 0;
        if (canSee != hadLine) this.seeTime = 0;
        if (canSee) ++this.seeTime;
        else --this.seeTime;

        float minD = this.minDistance;
        float maxD = this.maxDistance;
        double maxDistSq = maxD * maxD;

        if (!(distSq <= maxDistSq) || this.seeTime < 20) {
            mob.getNavigation().moveTo(target, this.moveSpeed);
            this.strafingTime = -1;
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            return;
        }

        mob.getNavigation().stop();
        ++this.strafingTime;

        if (this.strafingTime >= 20) {
            if (mob.getRandom().nextFloat() < 0.3F) this.strafingClockwise = !this.strafingClockwise;
            if (mob.getRandom().nextFloat() < 0.3F) this.strafingBackwards = !this.strafingBackwards;
            this.strafingTime = 0;
        }

        double dist = Math.sqrt(distSq);
        if (dist > maxD * 0.75) this.strafingBackwards = false;
        else if (dist < minD * 1.25) this.strafingBackwards = true;

        float forward = this.strafingBackwards ? -0.5F : 0.5F;
        float sideways = this.strafingClockwise ? 0.5F : -0.5F;

        if (this.strafingTime != -1) {
            mob.getMoveControl().strafe(forward, sideways);
        } else {
            mob.getMoveControl().strafe(0, 0);
        }

        mob.lookAt(target, 30.0F, 30.0F);
    }
}