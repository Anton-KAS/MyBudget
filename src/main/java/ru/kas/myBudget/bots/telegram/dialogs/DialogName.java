package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogName {
    ADD_ACCOUNT("addAcc"),

    NO("");

    private final String dialogName;

    DialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public String getDialogName() {
        return dialogName;
    }
}
