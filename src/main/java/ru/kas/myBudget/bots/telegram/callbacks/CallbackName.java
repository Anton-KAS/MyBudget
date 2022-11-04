package ru.kas.myBudget.bots.telegram.callbacks;

public enum CallbackName {
    MENU("menu"),
    ACCOUNTS("accounts"),
    ADD_ACCOUNT("addAccount"),
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
