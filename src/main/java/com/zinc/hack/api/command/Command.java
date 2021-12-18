package com.zinc.hack.api.command;

import com.zinc.hack.api.utils.ChatUtil;
import com.zinc.hack.api.wrapper.Wrapper;

public abstract class Command implements ICommand, Wrapper {

    private String label, syntax;
    private String[] alias;

    public Command(String syntax, String... alias) {
        this.alias = alias;
        this.syntax = syntax;
        this.label = alias[ 0 ];
    }

    public String getLabel() { return label; }

    public String[] getAlias() { return alias; }

    public String getSyntax() { return syntax; }


    @Override public abstract void execute(String[] args);

    protected void print(String message){
        ChatUtil.INSTANCE.sendMessage(message);
    }

    protected void sendUsage() { ChatUtil.INSTANCE.sendMessage("Invalid Command :/ " + getSyntax()); }

}
