package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.bots.telegram.dialogs.DialogName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;

import java.util.Objects;

public class AddAccountDescriptionText implements MessageText {
    private final Long userId;

    public AddAccountDescriptionText(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getText() {
        String title = Objects.requireNonNull(DialogsMap.getDialogMap(userId)).get(DialogName.ADD_ACCOUNT_TITLE.getDialogName());
        return "<b>Добавление счета</b>\n\n" +
                "1. Название: " + title + "\n" +
                "<b>2. Введите описание:</b>\n" +
                "<i>(для отмены введите /back)</i>";
    }
}
