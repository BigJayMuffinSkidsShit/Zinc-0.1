package com.zinc.hack.api.command;

import com.zinc.hack.api.utils.ChatUtil;
import com.zinc.hack.impl.commands.CoordianteCommand;
import com.zinc.hack.impl.commands.FriendCommand;
import com.zinc.hack.impl.commands.HelpCommand;
import com.zinc.hack.impl.commands.PrefixCommand;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CommandManager {
    private final List<Command> commands;

    private String prefix = ".";

    public CommandManager() {
        commands = new ArrayList<>();
        commands.addAll(Arrays.asList(
                new PrefixCommand(),
                new FriendCommand(),
                new CoordianteCommand(),
                new HelpCommand()
        ));

        commands.sort(Comparator.comparing(command -> command.getAlias()[0]));
    }

    public List<Command> getCommands() { return commands; }

    public void execute(String message) {
        String noPrefix = message.substring(this.prefix.length());
        Command command = getCommand(noPrefix.split(" ")[0]);
        if (command != null) {
            command.execute(Arrays.copyOfRange(noPrefix.split(" "), 1, noPrefix.split(" ").length));
            return;
        }

        // This will not be executed if command was found, since getCommand() returns null if it cannot find any command.
        ChatUtil.INSTANCE.sendInfoMessage("Command \"" + noPrefix.split(" ")[0] + TextFormatting.RED + "\" not found!");
    }

    public Command getCommand(String name) { return commands.stream().filter(command -> Arrays.stream(command.getAlias()).anyMatch(name::equalsIgnoreCase)).findFirst().orElse(null); }

    public String getPrefix() { return this.prefix; }

    public void setPrefix(String prefix) { this.prefix = prefix; }
}
