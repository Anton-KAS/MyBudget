package ru.kas.myBudget.bots.telegram.dialogs.util;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public enum DialogMapDefaultName {
    CURRENT_DIALOG_STEP("currentStep"),
    LAST_STEP("lastStep"),
    DIALOG_ID("dialogId"),
    START_FROM_ID("startFromId"),
    START_FROM_CALLBACK("startFromData"),
    EDIT_ID("editId"),
    NEXT("next"),
    PAGE("toPage"),
    CAN_SAVE("canSave"),
    CASH_ID("1"); // TODO: replace it

    private final String id;


    DialogMapDefaultName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
