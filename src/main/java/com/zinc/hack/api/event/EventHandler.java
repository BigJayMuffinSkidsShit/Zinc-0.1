package com.zinc.hack.api.event;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.command.Command;
import com.zinc.hack.api.event.events.EventChatMessage;
import com.zinc.hack.api.event.events.EventKey;
import com.zinc.hack.api.event.events.EventRenderWorldLast;
import com.zinc.hack.api.event.events.EventTick;
import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.wrapper.Wrapper;
import com.zinc.hack.impl.modules.client.Font;
import com.zinc.hack.impl.modules.client.Gui;
import com.zinc.hack.impl.modules.movement.Sprint;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author fuckyouthinkimboogieman
 */

// I'm basically too lazy to write it in mixins :^(
public class EventHandler implements Wrapper {

    @SubscribeEvent public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) Zinc.EVENTBUS.post(new EventKey(Keyboard.getEventKey()));
    }

    @SubscribeEvent public void onUpdate(TickEvent event) {
        Zinc.EVENTBUS.post(new EventTick());
    }

    @SubscribeEvent public void onWorldRender(RenderWorldLastEvent event) {
        Zinc.EVENTBUS.post(new EventRenderWorldLast(event.getContext(), event.getPartialTicks()));
    }

    @SubscribeEvent public void onMessage(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith(Zinc.commandManager.getPrefix())) {
            String temp = message.substring(1);
            for (Command command : Zinc.commandManager.getCommands()) {
                String[] split = temp.split(" ");
                for (String name : command.getAlias()) {
                    if (name.equalsIgnoreCase(split[ 0 ])) {
                        command.execute(split);
                        event.setCanceled(true);
                        mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                        return;
                    }
                }
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;
        //Main.getMain().getHoleManagement().register();
        Zinc.moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onTick);
    }

    @SubscribeEvent public void onRender2D(RenderGameOverlayEvent.Text text) {
        Zinc.moduleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::onRender2D);
    }

    @SubscribeEvent
    public void onRenderGameOverlayPre( RenderGameOverlayEvent.Pre event )
    {
        if( this == null ) return;

        if( event.getType( ) == RenderGameOverlayEvent.ElementType.HOTBAR )
            Zinc.moduleManager.getModules().stream().filter( Module::isEnabled ).forEach( m -> m.onRenderGameOverlay( event.getPartialTicks( ) ) );
    }

    @SubscribeEvent public void onRenderUpdate(RenderWorldLastEvent event) {
        Zinc.moduleManager.getModules().stream().filter(Module::isEnabled).forEach(m -> m.onRender3D(event.getPartialTicks()));
        //Main.getMain().getFpsManagement().update();
        //Main.getMain().getPulseManagement().update();
        Zinc.moduleManager.getModules().forEach(m -> {});
    }


}
