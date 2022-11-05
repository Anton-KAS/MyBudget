package ru.kas.myBudget.bots.telegram.callbacks;

public enum CallbackName {
    MENU("menu"),
    ACCOUNTS("accounts"),
    ADD_ACCOUNT("addAccount"),
    ADD_ACCOUNT_CURRENCY("addAccCurrency"),
    ADD_ACCOUNT_TYPE("addAccType"),
    ADD_ACCOUNT_BANK("addAccBank"),
    ADD_ACCOUNT_END("addAccEnd"),
    ADD_ACCOUNT_SAVE("addAccSave"),
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
