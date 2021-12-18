package com.zinc.hack.impl.modules.client;

import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.settings.Setting;

public class Font extends Module {

    public static Font instance;

    public Setting<Boolean> shadow = register("Shadow", true);

    public Font() {
        super("Font", "Using CFont, or not... Thats the question...", Category.Client);
        instance = this;

        setEnabled(true);
    }
}
