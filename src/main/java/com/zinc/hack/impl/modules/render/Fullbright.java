package com.zinc.hack.impl.modules.render;

import com.zinc.hack.api.module.Module;
//susssy owo
public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright","Gamma go brrrr",Category.Render);
    }

    public void onUpdate() {
        mc.gameSettings.gammaSetting = 1000;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
