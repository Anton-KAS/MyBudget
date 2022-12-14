package ru.kas.myBudget.bots.telegram.commands;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

public enum CommandNamesImpl implements CommandNames {
    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    CANCEL("/cancel"),
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
