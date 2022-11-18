package ru.kas.myBudget.bots.telegram.callbacks;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

public enum CallbackNamesImpl implements CommandNames {
    MENU("menu"),
    ACCOUNTS("accounts"),
    ACCOUNT("acc"),
    CANCEL_DIALOG("cancelDialog"),
    CLOSE("close"),
    NO("");

    private final String name;

    CallbackNamesImpl(String callbackName) {
        this.name = callbackName;
    }

    @Override
    public String getName() {
        return name;
    }
}
