package ru.kas.myBudget.bots.telegram.services;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;

public interface SendBotMessageService {
    void sendMessage(long chatId, String message);

    void sendMessageWithInlineKeyboard(long chatId, String message, InlineKeyboardMarkup markupInline);

    void editMessage(long chatId, long messageId, String message);

    void editMessageWithInlineKeyboard(long chatId, long messageId, String message, InlineKeyboardMarkup markupInline);

    void deleteMessage(long chatId, long messageId);

    default void execute(TelegramBot telegramBot, BotApiMethod message) {
        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // TODO Add logging to the project
        }
    }
}
