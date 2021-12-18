package com.zinc.hack.impl.commands;

import com.zinc.hack.api.command.Command;
import com.zinc.hack.api.command.CommandManager;
import com.zinc.hack.api.utils.ChatUtil;

public class HelpCommand extends Command {

    public CommandManager cmdmngr;

    public HelpCommand() {
        super("help", "help", "h");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.INSTANCE.sendErrorMessage("Not enough arguments!");
            return;
        }
        if (this == null) return;
        ChatUtil.INSTANCE.sendMessage("List of commands:" + cmdmngr.getCommands());
        }
    }
