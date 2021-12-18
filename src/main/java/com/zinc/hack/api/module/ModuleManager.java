package com.zinc.hack.api.module;

import com.zinc.hack.api.wrapper.Wrapper;
import com.zinc.hack.impl.modules.client.Font;
import com.zinc.hack.impl.modules.client.Gui;
import com.zinc.hack.impl.modules.combat.KillAura;
import com.zinc.hack.impl.modules.movement.Speed;
import com.zinc.hack.impl.modules.movement.Sprint;
import com.zinc.hack.impl.modules.movement.Step;
import com.zinc.hack.impl.modules.render.Fullbright;
import com.zinc.hack.impl.modules.world.FakePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager implements Wrapper {

    private final List<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        modules.addAll(Arrays.asList(
                /* Combat */
                new KillAura(),
                /* Movement */
                new Speed(),
                new Sprint(),
                new Step(),
                /* Render */
                new Fullbright(),
                /* Player */
                
                /* World */
                new FakePlayer(),
                /* Client */
                new Gui(),
                new Font()
        ));

        modules.sort(Comparator.comparing(Module::getName));
    }

    public List<Module> getModules() { return modules; }

    public List<Module> getModules(Module.Category category) { return modules.stream().filter(module -> module.getCategory() == category).collect(Collectors.toList()); }

    public Module getModule(String name) { return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null); }

}
