package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.services.TelegramUserService;

public interface MessageText {
    String getText();
    String getText(TelegramUserService telegramUserService, long userId);
}
