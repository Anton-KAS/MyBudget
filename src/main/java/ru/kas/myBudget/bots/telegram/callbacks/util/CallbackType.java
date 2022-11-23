package ru.kas.myBudget.bots.telegram.callbacks.util;

public enum CallbackType {
    NORMAL("cb"),
    DIALOG("dg");

    private final String id;

    CallbackType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
