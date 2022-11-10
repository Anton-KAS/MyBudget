package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

public enum AddAccountName {
    // Order is important!
    CURRENT_DIALOG_STEP("currentStep", null),
    START("start", "<b>Добавление нового счета</b>\n"),
    TYPE("addType", "%s - Тип: %s"),
    TITLE("addTitle", "%s - Название: %s"),
    DESCRIPTION("addDescription", "%s - Описание: %s"),
    CURRENCY("addCurrency", "%s - Валюта: %s"),
    BANK("addBank", "%s - Банк: %s"),
    CONFIRM("confirm", null),
    SAVE("accSave", null);

    private final String dialogId;
    private final String dialogTextPattern;

    AddAccountName(String dialogId, String dialogTextPattern) {
        this.dialogId = dialogId;
        this.dialogTextPattern = dialogTextPattern;
    }

    public String getDialogId() {
        return dialogId;
    }

    public String getDialogTextPattern() {
        return dialogTextPattern;
    }

    public String getDialogIdText() {
        return dialogId + "Text";
    }

    public static String getDialogNameByOrder(int order) {
        return AddAccountName.values()[order].getDialogId();
    }

}
