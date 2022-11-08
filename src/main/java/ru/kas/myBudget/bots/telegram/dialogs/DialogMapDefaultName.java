package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogMapDefaultName {
    LAST_STEP("lastStep"),
    DIALOG_ID("dialogId"),
    START_FROM_ID("startFrom"),
    NEXT("next"),
    CASH_ID("1");

    private final String id;


    DialogMapDefaultName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
