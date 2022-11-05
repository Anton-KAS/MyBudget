package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.bots.telegram.dialogs.DialogName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;

import java.util.Objects;

public class AddAccountCurrencyText implements MessageText {
    private final Long userId;

    public AddAccountCurrencyText(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getText() {
        String title = Objects.requireNonNull(DialogsMap.getDialogMap(userId)).get(DialogName.ADD_ACCOUNT_TITLE.getDialogName());
        String description = Objects.requireNonNull(DialogsMap.getDialogMap(userId)).get(DialogName.ADD_ACCOUNT_DESCRIPTION.getDialogName());
        return "<b>Добавление счета</b>\n\n" +
                "1. Название: " + title + "\n" +
                "2. Описание: " + description + "\n" +
                "<b>3. Выберете валюту счета:</b>\n" +
                "<i>(для отмены введите /back)</i>";
    }
}
