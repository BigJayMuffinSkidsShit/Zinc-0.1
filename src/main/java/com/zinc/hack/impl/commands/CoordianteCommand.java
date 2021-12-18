package com.zinc.hack.impl.commands;

import com.zinc.hack.api.command.Command;
import com.zinc.hack.api.utils.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CoordianteCommand extends Command {

    public CoordianteCommand() {
        super("cords", "cords", "c");
    }

    @Override public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.INSTANCE.sendErrorMessage("Not enough arguments!");
            return;
        }
        if (this == null) return;
        ChatUtil.INSTANCE.sendMessage("coordinates has copied [" + String.format("XYZ: %s %s %s", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) + "]");
        StringSelection selection = new StringSelection(String.format("XYZ: %s %s %s", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
