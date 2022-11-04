package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogName {
    CURRENT_DIALOG_STEP("currentStep"),
    ADD_ACCOUNT_TITLE("addAccountTitle"),
    ADD_ACCOUNT_DESCRIPTION("addAccountDescription"),
    NO("");

    private final String dialogName;

    DialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public String getDialogName() {
        return dialogName;
    }
}
