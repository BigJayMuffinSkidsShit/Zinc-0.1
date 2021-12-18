package com.zinc.hack.api.event.events;

import com.zinc.hack.api.event.Event;
import net.minecraft.client.renderer.RenderGlobal;

/**
 * @author fuckyouthinkimboogieman
 */

public class EventRenderWorldLast extends Event {
    private final RenderGlobal context;
    private final float partialTicks;

    public EventRenderWorldLast(RenderGlobal context, float partialTicks) {
        this.context = context;
        this.partialTicks = partialTicks;
    }

    public RenderGlobal getContext() { return context; }

    public float getPartialTicks() { return partialTicks; }
}
