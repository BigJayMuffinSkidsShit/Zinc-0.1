package com.zinc.hack.mixin.mixins;

import com.zinc.hack.mixin.MixinHelper;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onPacketReceive(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo ci) {
        MixinHelper.onPacketRecieve(context, packet, ci);
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onPacketSend(final Packet<?> packet, final CallbackInfo ci) {
        MixinHelper.onPacketSend(packet, ci);
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"))
    private void onPostSendPacket(Packet<?> packet, CallbackInfo ci) {
        MixinHelper.onPostPacket(packet, ci);
    }

}