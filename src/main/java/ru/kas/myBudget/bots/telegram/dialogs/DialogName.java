package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogName {
    CURRENT_DIALOG_STEP("currentStep"),
    ADD_ACCOUNT_TITLE("addAccTitle"),
    ADD_ACCOUNT_DESCRIPTION("addAccDescription"),


    NO("");

    private final String dialogName;

    DialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public String getDialogName() {
        return dialogName;
    }
}
