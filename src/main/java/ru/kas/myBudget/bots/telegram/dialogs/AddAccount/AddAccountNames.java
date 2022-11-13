package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

public enum AddAccountNames implements CommandNames {
    // Order is important!
    START("start", "<b>Добавление нового счета</b>\n"),
    TYPE("addType", "%s - Тип: %s"),
    TITLE("addTitle", "%s - Название: %s"),
    DESCRIPTION("addDescription", "%s - Описание: %s"),
    CURRENCY("addCurrency", "%s - Валюта: %s"),
    BANK("addBank", "%s - Банк: %s"),
    START_BALANCE("addStartBalance", "%s - Начальный баланс: %s"),
    CONFIRM("confirm", null),
    SAVE("accSave", null);

    private final String name;
    private final String dialogTextPattern;

    AddAccountNames(String name, String dialogTextPattern) {
        this.name = name;
        this.dialogTextPattern = dialogTextPattern;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDialogTextPattern() {
        return dialogTextPattern;
    }

    public String getDialogIdText() {
        return name + "Text";
    }

    public static String getDialogNameByOrder(int order) {
        return AddAccountNames.values()[order].getName();
    }

}
