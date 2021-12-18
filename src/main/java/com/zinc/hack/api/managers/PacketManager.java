package com.zinc.hack.api.managers;

import com.zinc.hack.api.wrapper.Wrapper;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayDeque;

public class PacketManager extends ArrayDeque<Packet> implements Wrapper {

    private static final PacketManager INSTANCE = new PacketManager();

    @SubscribeEvent public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.world == null || mc.player == null) return;
        for (int j = 0; j < 6; j++) {
            if (peekFirst() != null) mc.player.connection.sendPacket(pollFirst());
        }
    }

    public static void send(Packet packet) {
        getInstance().add(packet);
    }

    public static PacketManager getInstance() { return INSTANCE; }

}