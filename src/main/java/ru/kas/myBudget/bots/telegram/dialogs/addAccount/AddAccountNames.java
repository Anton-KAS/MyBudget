package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import ru.kas.myBudget.bots.telegram.dialogs.CommandDialogNames;

public enum AddAccountNames implements CommandDialogNames {
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
    private final String stepTextPattern;

    AddAccountNames(String name, String stepTextPattern) {
        this.name = name;
        this.stepTextPattern = stepTextPattern;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStepTextPattern() {
        return stepTextPattern;
    }

    @Override
    public String getStepIdText() {
        return name + "Text";
    }

}
