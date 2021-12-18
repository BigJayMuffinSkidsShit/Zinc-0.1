package com.zinc.hack.api.command;

@FunctionalInterface
public interface ICommand {

    void execute(String[] args);

}
