package com.zinc.hack.mixin;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.event.events.EventMove;
import com.zinc.hack.api.event.events.EventPacket;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.MoverType;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author fuckyouthinkimboogieman
 */

// Put all the mixin code here, so we can obfuscate this class, but don't obfuscate mixins
public class MixinHelper {
    public static EventMove onMove(final MoverType moverType, final double x, final double y, final double z) {
        EventMove event = new EventMove(moverType, x, y, z);
        Zinc.EVENTBUS.post(event);
        return event;
    }

    public static void onPacketRecieve(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo ci) {
        final EventPacket.Receive event = new EventPacket.Receive(packet);
        Zinc.EVENTBUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    public static void onPacketSend(final Packet<?> packet, final CallbackInfo ci) {
        final EventPacket.Send event = new EventPacket.Send(packet);
        Zinc.EVENTBUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    public static void onPostPacket(Packet<?> packet, CallbackInfo ci) {
        final EventPacket.Post event = new EventPacket.Post(packet);
        Zinc.EVENTBUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
