package com.zinc.hack.impl.modules.world;

import com.mojang.authlib.GameProfile;
import com.zinc.hack.api.module.Module;
import com.zinc.hack.api.settings.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", Category.World);
    }

    Setting<String> fakename = register("Name", "Cat");

    private EntityOtherPlayerMP clonedPlayer;

    public void onEnable() {
        if (mc.player == null || mc.player.isDead) {
            disable();
            return;
        }

        clonedPlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("2099644b-d43e-447f-b490-e93398833839"), fakename.getValueAsString()));
        clonedPlayer.copyLocationAndAnglesFrom(mc.player);
        clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
        clonedPlayer.rotationYaw = mc.player.rotationYaw;
        clonedPlayer.rotationPitch = mc.player.rotationPitch;
        clonedPlayer.setGameType(GameType.SURVIVAL);
        clonedPlayer.setHealth(20);
        mc.world.addEntityToWorld(-12345, clonedPlayer);
        clonedPlayer.onLivingUpdate();
    }

    public void onDisable() {
        if (mc.world != null) {
            mc.world.removeEntityFromWorld(-12345);
        }
    }

    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (isEnabled()){
            disable();
        }
    }
}
