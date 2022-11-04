package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.services.TelegramUserService;

public class AddAccountTitleText implements MessageText {
    public AddAccountTitleText() {
    }

    @Override
    public String getText() {
        return "<b>Добавление счета\n</b>" +
                "1. Введите название счета:\n" +
                "<i>(для отмены введите /back)</i>";
    }

    @Override
    public String getText(TelegramUserService telegramUserService, long userId) {
        return null;
    }
}
