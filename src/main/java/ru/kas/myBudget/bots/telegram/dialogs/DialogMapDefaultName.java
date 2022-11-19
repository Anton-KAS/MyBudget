package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogMapDefaultName {
    CURRENT_DIALOG_STEP("currentStep"),
    LAST_STEP("lastStep"),
    DIALOG_ID("dialogId"),
    START_FROM_ID("startFromId"),
    START_FROM_DATA("startFromData"),
    EDIT_ID("editId"),
    NEXT("next"),
    PAGE("tpPage"),
    CASH_ID("1");

    private final String id;


    DialogMapDefaultName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
