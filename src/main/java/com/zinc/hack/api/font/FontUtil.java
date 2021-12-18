package com.zinc.hack.api.font;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.wrapper.Wrapper;
import com.zinc.hack.impl.modules.client.Font;

public class FontUtil implements Wrapper {
    public static final CFontRenderer fontRenderer = new CFontRenderer(19.0f);

    public static void drawText(String text, float x, float y, int color) {
        if (Font.instance.shadow.getValue()) {
            if (Zinc.moduleManager.getModule("Font").isEnabled()) {
                fontRenderer.drawStringWithShadow(text, x, y, color);
            } else {
                mc.fontRenderer.drawStringWithShadow(text, x, y, color);
            }
        } else {
            if (Zinc.moduleManager.getModule("Font").isEnabled()) {
                fontRenderer.drawString(text, x, y, color);
            } else {
                mc.fontRenderer.drawString(text, (int) x, (int) y, color);
            }
        }
    }

    public static void drawCFontText(String text, float x, float y, int color) {
        if (Font.instance.shadow.getValue().booleanValue()) fontRenderer.drawStringWithShadow(text, x, y, color);
        else fontRenderer.drawString(text, x, y, color);
    }

    public static float getCFontTextWidth(String text) {
        return fontRenderer.getWidth(text);
    }
}
