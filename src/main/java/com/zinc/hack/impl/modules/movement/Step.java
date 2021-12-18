package com.zinc.hack.impl.modules.movement;

import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.settings.Setting;

public class Step extends Module {
    public static Step instance;
    public final Setting<Double> height = register("Height", 2.0, 0.0, 2.0, 0);
    private Setting setting;

    public Step() {
        super("Step", "Steps", Category.Movement);
    }
    
    public Setting<Double> register(String name, double value , double min, double max, int decimalplaecs) {
        return this.setting;
    }

    public void onUpdate() {
        mc.player.stepHeight = 2f;
    }

    public void onDisable() {
        mc.player.stepHeight = 0.5f;
    }
}
