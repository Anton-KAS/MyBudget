package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {
    void execute(Update update);

    default long getUserId(Update update) {
        return update.getCallbackQuery().getFrom().getId();
    }

    default long getChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }

    default long getMessageId(Update update) {
        return update.getCallbackQuery().getMessage().getMessageId();
    }
}
