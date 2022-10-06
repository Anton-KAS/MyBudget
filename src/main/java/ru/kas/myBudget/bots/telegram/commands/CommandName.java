package ru.kas.myBudget.bots.telegram.commands;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    NO("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
