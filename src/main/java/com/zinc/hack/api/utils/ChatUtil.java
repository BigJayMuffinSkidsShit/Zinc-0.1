package com.zinc.hack.api.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.zinc.hack.api.wrapper.Wrapper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
    public enum ChatUtil implements Wrapper {
        INSTANCE;

        public String staticName = ChatFormatting.AQUA + "[" + ChatFormatting.AQUA + "Zinc" + ChatFormatting.AQUA + "]";

        public void sendMessage(String text) {
            sendMsgEvent(staticName, text, false, 1);
        }

        public void sendInfoMessage(String text) {
            sendMsgEvent(staticName, ChatFormatting.GOLD + "[INFO] " + ChatFormatting.YELLOW + text, false, 1);
        }

        public void sendErrorMessage(String text) {
            sendMsgEvent(staticName, ChatFormatting.DARK_RED + "[ERROR] " + ChatFormatting.RED + text, false, 1);
        }

        public void sendMessage(String text, Boolean silent) {
            sendMsgEvent(staticName, text, silent, 1);
        }

        public void sendMessageId(String text, Boolean silent, int id) {
            sendMsgEvent(staticName, text, silent, id);
        }

        public void sendMsgEvent(String prefix, String text, boolean silent, int id) {
            if (mc.player == null) return;
            if (!silent) {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(prefix + TextFormatting.GRAY + " " + text));
            } else {
                mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(prefix + TextFormatting.GRAY + " " + text), id);
            }
        }

    }
