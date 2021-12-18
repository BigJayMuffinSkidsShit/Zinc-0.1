package com.zinc.hack.impl.modules.client;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.settings.Setting;
import org.lwjgl.input.Keyboard;

public class Gui extends Module {
    public static Gui instance;

    public Gui() {
        super("GUI", "mod menu", Category.Client);
        setKey(Keyboard.KEY_RCONTROL);
        instance = this;
    }

    public Setting<Double> red = register("Red", 0, 0, 255, 0);
    public Setting<Double> green = register("Green", 0, 0, 255, 0);
    public Setting<Double> blue = register("Blue", 0, 0, 255, 0);

    public void onEnable() {
        mc.displayGuiScreen(Zinc.gui);
        disable();
    }
}
