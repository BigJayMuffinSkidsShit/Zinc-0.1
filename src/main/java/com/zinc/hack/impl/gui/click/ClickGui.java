package com.zinc.hack.impl.gui.click;

import com.zinc.hack.api.module.Module;
import com.zinc.hack.impl.modules.client.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {

    public static List<Frame> frames;
    public static Color color;

    public ClickGui() {
        frames = new ArrayList<>();
        int frameX = 10;

        for (final Module.Category category : Module.Category.values()) {
            final Frame frame = new Frame(category);
            frame.setX(frameX);
            ClickGui.frames.add(frame);
            frameX += frame.getWidth() + 10;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        color = new Color(Gui.instance.red.getValue().intValue(), Gui.instance.green.getValue().intValue(), Gui.instance.blue.getValue().intValue());
        frames.forEach(frame -> {
            frame.renderFrame();
            frame.updatePosition(mouseX, mouseY);
            frame.getComponents().forEach(c -> c.updateComponent(mouseX, mouseY));
        });
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (final Frame frame : frames) {
            if (frame.isHover(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isHover(mouseX, mouseY) && mouseButton == 1) frame.setOpen(!frame.isOpen());

            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (final Frame frame : frames) {
            frame.setDrag(false);
        }
        for (final Frame frame : frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }

    public void drawGradient(int left, int top, int right, int bottom, int startColor, int endColor) {
        drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(keyCode);
                }
            }
        }

        System.out.println(typedChar);

        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);

            if (mc.currentScreen == null) {
                mc.setIngameFocus();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

}
