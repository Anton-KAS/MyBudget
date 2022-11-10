package ru.kas.myBudget.bots.telegram.callbacks;

public enum CallbackName {
    MENU("menu"),
    ACCOUNTS("accounts"),
    CANCEL_DIALOG("cancelDialog"),
    CLOSE("close"),
    NO("");

    private final String callbackName;

    CallbackName(String callbackName) {
        this.callbackName = callbackName;
    }

    public String getCallbackName() {
        return callbackName;
    }
}
