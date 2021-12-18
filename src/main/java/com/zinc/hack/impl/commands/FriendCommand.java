package com.zinc.hack.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.zinc.hack.api.command.Command;
import com.zinc.hack.api.friends.FriendManager;
import com.zinc.hack.api.utils.ChatUtil;

import java.util.Arrays;


public class FriendCommand extends Command {

    public FriendCommand() {
        super("f add/del <nick>", "f", "Friend", "friend");
    }

    @Override public void execute(String[] str) {
        if (str.length == 0) {
            ChatUtil.INSTANCE.sendErrorMessage("Not enough arguments!");
            return;
        }
        str = Arrays.copyOfRange(str, 1, str.length);
        if (str.length < 2) {
            sendUsage();
            return;
        } else {
            switch (str[0].toLowerCase()) {
                case "add":
                    if (FriendManager.getInstance().is(str[1].toLowerCase())) return;
                    FriendManager.getInstance().add(str[1]);
                    print(ChatFormatting.GRAY + "friend " + ChatFormatting.WHITE + "> " + str[1].toLowerCase() + " " + ChatFormatting.GREEN + "added");
                    break;
                case "del":
                    FriendManager.getInstance().remove(str[1]);
                    print(ChatFormatting.GRAY + "friend " + ChatFormatting.WHITE + "> " + str[1].toLowerCase() + " " + ChatFormatting.RED + "removed");
                    break;
            }
        }
    }
}
