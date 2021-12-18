package com.zinc.hack.impl.modules.render;

import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.settings.Setting;
import net.minecraft.client.settings.GameSettings;

public class CustomFOV extends Module {
    public CustomFOV() {
        super("CustomFOV", "changes FOV", Category.Render);
    }
    public static CustomFOV INSTANCE;
    public Setting<Double> amount = this.register("Amount",110,-180,180,0);

    public static CustomFOV getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(CustomFOV INSTANCE) {
        CustomFOV.INSTANCE = INSTANCE;
    }

    public void onUpdate() {
        mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, amount.getValue().floatValue());
     }
}

