package com.zinc.hack.impl.modules.combat;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.settings.Setting;
import com.zinc.hack.api.utils.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class KillAura extends Module {
    public KillAura() {
        super("Kill Aura", "kills ppl", Category.Combat);
    }

    public static Entity target;
    private final TimerUtil timer = new TimerUtil();
    public Setting<Double> range = register("Range", 6, 0, 7,2);
    public Setting<Boolean> delay = register("HitDelay", Boolean.TRUE);
    public Setting<Boolean> rotate = register("Rotate", Boolean.FALSE);
    public Setting<Boolean> onlySharp = register("SwordOnly", Boolean.TRUE);
    public Setting<Double> raytrace = register("WallRange", 4.5, 1, 5, 2);
    public Setting<Boolean> players = register("Players", Boolean.TRUE);
    public Setting<Boolean> packet = register("Packet", Boolean.FALSE);

    public void onTick() {
        if (!rotate.getValue())
            doKillaura();
    }

    public void onUpdate() {
        doKillaura();
    }

    private void doKillaura() {
        if (onlySharp.getValue() && !EntityUtil.holdingWeapon(mc.player)) {
            target = null;
            return;
        }
        int wait = !delay.getValue() ? 0 : (int) (DamageUtil.getCooldownByWeapon(mc.player));
        if (!timer.passedMs(wait))
            return;
        target = getTarget();
        if (target == null)
            return;
        EntityUtil.attackEntity(target, packet.getValue(), true);
        timer.reset();
        if (rotate.getValue()) {
            RotationUtil.faceEntity(target);
        }
    }

    private Entity getTarget() {
        Entity target = null;
        double distance = range.getValue().floatValue();
        double maxHealth = 36.0D;
        for (Entity entity : mc.world.playerEntities) {
            if (((!players.getValue() || !(entity instanceof EntityPlayer)) && EntityUtil.isntValid(entity, distance)))
                continue;
            if (!mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && mc.player.getDistanceSq(entity) > MathUtil.square(raytrace.getValue().floatValue()))
                continue;
            if (target == null) {
                target = entity;
                distance = mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
                continue;
            }
            if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer) entity, 18)) {
                target = entity;
                break;
            }
            if (mc.player.getDistanceSq(entity) < distance) {
                target = entity;
                distance = mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
            }
            if (EntityUtil.getHealth(entity) < maxHealth) {
                target = entity;
                distance = mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
            }
        }
        return target;
    }

    public String getDisplayInfo() {
        if (target instanceof EntityPlayer)
            return target.getName();
        return null;
    }


}