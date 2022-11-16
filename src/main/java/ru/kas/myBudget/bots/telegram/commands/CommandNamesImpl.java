package ru.kas.myBudget.bots.telegram.commands;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

public enum CommandNamesImpl implements CommandNames {
    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    STAT("/stat"),
    MENU("/menu"),
    ACCOUNTS("/accounts"),
    NO("");

    private final String name;

    CommandNamesImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
