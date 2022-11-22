package ru.kas.myBudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class UpdateParameter {
    public static long getUserId(Update update) {
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getFrom().getId();
        else return update.getMessage().getFrom().getId();
    }

    public static long getChatId(Update update) {
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getChatId();
        else return update.getMessage().getChatId();
    }

    public static int getMessageId(Update update) {
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getMessageId();
        else return update.getMessage().getMessageId();
    }

    public static String getMessageText(Update update) {
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getText().trim();
        return update.getMessage().getText().trim();
    }

    public static Optional<String[]> getCallbackData(Update update) {
        if (update.hasCallbackQuery()) return Optional.of(update.getCallbackQuery().getData().split("_"));
        return Optional.empty();
    }
}
