package com.zinc.hack.api.event.events;

import com.zinc.hack.api.event.Event;
import net.minecraft.entity.MoverType;

public class EventMove extends Event {

    public MoverType moverType;
    public double motionX;
    public double motionY;
    public double motionZ;

    public EventMove(MoverType moverType, double motionX, double motionY, double motionZ) {
        this.moverType = moverType;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
}
