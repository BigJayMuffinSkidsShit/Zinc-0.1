package com.zinc.hack;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.zinc.hack.api.command.CommandManager;
import com.zinc.hack.api.event.EventHandler;
import com.zinc.hack.api.event.events.EventChatMessage;
import com.zinc.hack.api.event.events.EventKey;
import com.zinc.hack.api.event.events.EventRenderWorldLast;
import com.zinc.hack.api.event.events.EventTick;
import com.zinc.hack.api.friends.FriendManager;
import com.zinc.hack.api.managers.PacketManager;
import com.zinc.hack.api.managers.RotationManager;
import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.module.ModuleManager;
import com.zinc.hack.api.settings.SettingManager;
import com.zinc.hack.api.wrapper.Wrapper;
import com.zinc.hack.impl.gui.click.ClickGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(modid = Zinc.MOD_ID, name = Zinc.NAME, version = Zinc.VERSION)
public class Zinc implements Wrapper {
    public static final String MOD_ID = "zinc";
    public static final String NAME = "Zinc";
    public static final String VERSION = "0.1";

    public static final Logger logger = (Logger) LogManager.getLogger("Zinc");

    public static final EventBus EVENTBUS = new EventBus();

    public static FriendManager friendManager;
    public static SettingManager settingManager;
    public static ModuleManager moduleManager;
    public static ClickGui gui;
    public static CommandManager commandManager;
    public static RotationManager rotationManager;
    public static PacketManager packetManager;

    @Mod.EventHandler public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        EVENTBUS.register(this);

        logger.info("_kisman_ fan - BigJMuffin");
        logger.info("BigJMuffin fan)))) - _kisman_");
    }

    @Mod.EventHandler public void init(FMLInitializationEvent event) {
        Display.setTitle("Zinc v0.1");
        friendManager = new FriendManager();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        rotationManager = new RotationManager();
        packetManager = new PacketManager();
        gui = new ClickGui();

    }
    
    public void renderOverlay(RenderGameOverlayEvent event) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        FontRenderer fontRenderer= mc.fontRenderer;

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            fontRenderer.drawStringWithShadow(Zinc.NAME + VERSION, 1,2, 0xa300ff);
        }
    }

    @Mod.EventHandler public void postInit(FMLPostInitializationEvent event) {}

    @Subscribe
    public void onUpdate(EventTick event) {
        moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    @Subscribe public void onRender(EventRenderWorldLast eventRenderWorldLast) {
        moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onRender);
    }

    @Subscribe public void onKey(EventKey event) {
        if (mc.player != null && mc.world != null) Zinc.moduleManager.getModules().stream().filter
                (module -> module.getKey() == Keyboard.getEventKey()).forEach(Module::toggle);
    }

    @Subscribe public void onMessage(EventChatMessage event) {
        if (event.getMessage().startsWith(commandManager.getPrefix())) commandManager.execute(event.getMessage());
    }
}
