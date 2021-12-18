package com.zinc.hack.api.font;

import com.zinc.hack.api.wrapper.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author olliem5
 * @author ionar2
 * @author Hyperion Client
 */
public final class CFontRenderer implements Wrapper {
    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile(COLOR_CHAR + "[0123456789abcdefklmnor]");
    private final int[] colorCodes = {0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};
    private final Map<String, Float> cachedStringWidth = new HashMap<>();
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor;
    private final float size;

    public CFontRenderer(float fontSize) {
        size = fontSize;
        ScaledResolution resolution = new ScaledResolution(mc);

        try {
            prevScaleFactor = resolution.getScaleFactor();
            unicodeFont = new UnicodeFont(CFontRenderer.getFont().deriveFont(fontSize * prevScaleFactor / 2));
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();

            prevScaleFactor = resolution.getScaleFactor();

            try {
                unicodeFont = new UnicodeFont(getFont().deriveFont(fontSize * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        this.antiAliasingFactor = resolution.getScaleFactor();
    }

    public static Font getFont() throws IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, CFontRenderer.class.getResourceAsStream("/assets/fonts/Verdana.ttf"));
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }

        ScaledResolution resolution = new ScaledResolution(mc);

        try {
            if (resolution.getScaleFactor() != prevScaleFactor) {
                prevScaleFactor = resolution.getScaleFactor();
                unicodeFont = new UnicodeFont(getFont().deriveFont(size * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            }
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }

        this.antiAliasingFactor = resolution.getScaleFactor();

        GL11.glPushMatrix();
        GlStateManager.scale(1 / antiAliasingFactor, 1 / antiAliasingFactor, 1 / antiAliasingFactor);

        x *= antiAliasingFactor;
        y *= antiAliasingFactor;

        float originalX = x;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float alpha = (float) (color >> 24 & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);

        int currentColor = color;

        char[] characters = text.toCharArray();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        String[] parts = COLOR_CODE_PATTERN.split(text);

        int index = 0;

        for (String s : parts) {
            for (String s2 : s.split("\n")) {
                for (String s3 : s2.split("\r")) {
                    unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(currentColor));
                    x += unicodeFont.getWidth(s3);

                    index += s3.length();

                    if (index < characters.length && characters[index] == '\r') {
                        x = originalX;

                        index++;
                    }
                }

                if (index < characters.length && characters[index] == '\n') {
                    x = originalX;
                    y += getHeight(s2) * 2;

                    index++;
                }
            }

            if (index < characters.length) {
                char colorCode = characters[index];
                if (colorCode == COLOR_CHAR) {
                    char colorChar = characters[index + 1];
                    int codeIndex = ("0123456789" + "abcdef").indexOf(colorChar);
                    if (codeIndex < 0) {
                        if (colorChar == 'r') {
                            currentColor = color;
                        }
                    } else {
                        currentColor = colorCodes[codeIndex];
                    }
                    index += 2;
                }
            }
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.bindTexture(0);
        GlStateManager.popMatrix();

        return (int) getWidth(text);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        if (text == null || text == "") {
            return;
        }

        drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0x000000);

        drawString(text, x, y, color);
    }

    public float getWidth(String text) {
        if (cachedStringWidth.size() > 1000) {
            cachedStringWidth.clear();
        }
        return cachedStringWidth.computeIfAbsent(text, e -> unicodeFont.getWidth(stripColor(text)) / antiAliasingFactor);
    }

    public float getHeight(String s) {
        return unicodeFont.getHeight(s) / 2.0F;
    }

    public float getStringWidth(String text) {
        return unicodeFont.getWidth(stripColor(text)) / 2;
    }

    public float getStringHeight(String text) {
        return getHeight(text);
    }

    public static String stripColor(final String input) {
        return input == null ? null : Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-OR]").matcher(input).replaceAll("");
    }
}
