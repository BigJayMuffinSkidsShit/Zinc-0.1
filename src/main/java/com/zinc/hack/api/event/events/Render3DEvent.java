package com.zinc.hack.api.event.events;

import com.zinc.hack.api.event.Event;

public class Render3DEvent extends Event
{
    private float partialTicks;

    public Render3DEvent( float partialTicks )
    {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks( )
    {
        return partialTicks;
    }
}
