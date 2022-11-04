package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.bots.telegram.dialogs.DialogName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Objects;

public class AddAccountDescriptionText implements MessageText {
    public AddAccountDescriptionText() {

    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getText(TelegramUserService telegramUserService, long userId) {
        String title = Objects.requireNonNull(DialogsMap.getDialogMap(userId)).get(DialogName.ADD_ACCOUNT_TITLE.getDialogName());
        return "<b>Добавление счета\n</b>" +
                "1. название: " + title + "\n" +
                "2. Введите описание:" +
                "<i>(для отмены введите /back)</i>";
    }
}
