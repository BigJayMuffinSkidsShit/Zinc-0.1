package com.zinc.hack.impl.gui.click;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.font.FontUtil;
import com.zinc.hack.api.module.Module;
import com.zinc.hack.impl.gui.click.components.ModuleButton;

import java.util.ArrayList;

public class Frame {

    public Module.Category category;
    public ArrayList<Component> components;
    private boolean open;
    private final int width;
    private int y;
    private int x;
    private final int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private int height;

    public Frame(Module.Category category) {
        this.category = category;
        this.components = new ArrayList<>();
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 16;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int componentY = this.barHeight;
        for (Module m : Zinc.moduleManager.getModules(category)) {
            ModuleButton moduleButton = new ModuleButton(m, this, componentY);
            components.add(moduleButton);
            componentY += 16;
        }
        update();
    }

    public void renderFrame() {
        Zinc.gui.drawGradient(x, y, x + width, y + barHeight, ClickGui.color.getRGB(), ClickGui.color.getRGB());
        FontUtil.drawCFontText(category.name(), x + width / 2 - FontUtil.getCFontTextWidth(category.name()) / 2, y + 1, -1);
        if (open && !components.isEmpty()) {
            components.forEach(Component::render);
        }
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setX(final int newX) {
        this.x = newX;
    }

    public void setY(final int newY) {
        this.y = newY;
    }

    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }

    public void update() {
        int off = this.barHeight;
        for (final Component comp : this.components) {
            comp.setOffset(off);
            off += comp.getHeight();
        }
        this.height = off;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }

    public boolean isHover(final double x, final double y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }

}
