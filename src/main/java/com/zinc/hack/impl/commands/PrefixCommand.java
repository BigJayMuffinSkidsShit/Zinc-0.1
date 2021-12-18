package com.zinc.hack.impl.commands;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.command.Command;
import com.zinc.hack.api.utils.ChatUtil;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super( "prefix", "prefix", "p");
    }

    @Override public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.INSTANCE.sendErrorMessage("Not enough arguments!");
            return;
        }

        Zinc.commandManager.setPrefix(args[0]);
        ChatUtil.INSTANCE.sendMessage("Prefix was set to " + Zinc.commandManager.getPrefix());
    }
}
