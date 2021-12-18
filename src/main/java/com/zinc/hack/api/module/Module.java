package com.zinc.hack.api.module;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.settings.Setting;
import com.zinc.hack.api.wrapper.Wrapper;

import java.util.Arrays;

/**
 * @author fuckyouthinkimboogieman
 */

public class Module implements Wrapper {
    private final String name, description;
    private final Category category;

    private int key = -1;

    private boolean enabled = false;

    public Module(String name, Category category) {
        super();
        this.name = name;
        this.description = "No description provided.";
        this.category = category;
    }

    public Module(String name, String description, Category category) {
        super();
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public Category getCategory() { return this.category; }

    public int getKey() { return this.key; }

    public void setKey(int key) { this.key = key; }

    public boolean isEnabled() { return this.enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public void toggle() { if (isEnabled()) disable(); else enable(); }

    public void enable() {
        setEnabled(true);
        onEnable();
    }

    public void disable() {
        setEnabled(false);
        onDisable();
    }

    public void onEnable() { Zinc.EVENTBUS.register(this); }

    public void onDisable() { Zinc.EVENTBUS.unregister(this); }

    public void onUpdate() {}

    public void onRender() {}

    public void onTick() {}

    public void onRender2D() {}

    public void onRender3D(float partialTicks) {}

    public void onRenderGameOverlay(float partialTicks) {}

    public Setting<Boolean> register(String name, boolean value) {
        Setting<Boolean> setting = new Setting<>(name, this, getCategory(), value);
        Zinc.settingManager.register(setting);
        return setting;
    }

    public Setting<Double> register(String name, double value, double min, double max, int decimalPlaces) {
        Setting<Double> setting = new Setting<>(name, this, getCategory(), value, min, max, decimalPlaces);
        Zinc.settingManager.register(setting);
        return setting;
    }

    public Setting<Float> register(String name, float value, float min, float max) {
        Setting<Float> setting = new Setting<>(name, this, getCategory(), value, min, max);
        Zinc.settingManager.register(setting);
        return setting;
    }

    public Setting<Integer> register(String name, int value, int min, int max) {
        Setting<Integer> setting = new Setting<>(name, this, getCategory(), value, min, max);
        Zinc.settingManager.register(setting);
        return setting;
    }

    public Setting<String> register(String name, String value, String... modes) {
        Setting<String> setting = new Setting<>(name, this, getCategory(), Arrays.asList(modes), value);
        Zinc.settingManager.register(setting);
        return setting;
    }

    public enum Category {
        Combat,
        Movement,
        Render,
        Player,
        World,
        Client
    }
}
