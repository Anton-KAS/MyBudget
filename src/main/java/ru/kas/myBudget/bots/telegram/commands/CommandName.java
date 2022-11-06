package ru.kas.myBudget.bots.telegram.commands;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    STAT("/stat"),
    MENU("/menu"),
    ACCOUNTS("/accounts"),
    NO("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
