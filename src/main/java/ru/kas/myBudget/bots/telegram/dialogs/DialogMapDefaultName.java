package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogMapDefaultName {
    START_FROM_ID("startFrom"),
    CASH_ID("1");

    private final String id;


    DialogMapDefaultName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
