package ru.kas.myBudget.bots.telegram.dialogs.account;

import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum AccountNames implements CommandDialogNames {
    // Order is important!
    START("start", "<b>Добавление нового счета</b>\n"),
    TYPE("addType", "%s - Тип:  <b><i>%s</i></b>"),
    TITLE("addTitle", "%s - Название:  <b><i>%s</i></b>"),
    DESCRIPTION("addDescription", "%s - Описание:  <b><i>%s</i></b>"),
    CURRENCY("addCurrency", "%s - Валюта:  <b><i>%s</i></b>"),
    BANK("addBank", "%s - Банк:  <b><i>%s</i></b>"),
    START_BALANCE("addStartBalance", "%s - Начальный баланс:  <b><i>%s</i></b>"),
    CONFIRM("confirm", null),
    SAVE("accSave", null);

    private final String name;
    private final String stepTextPattern;

    AccountNames(String name, String stepTextPattern) {
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
