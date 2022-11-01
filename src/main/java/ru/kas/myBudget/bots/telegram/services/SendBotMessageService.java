package ru.kas.myBudget.bots.telegram.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;

import java.util.List;

public interface SendBotMessageService {
    void sendMessage(long chatId, String message);

    void sendMessageWithInlineKeyboard(long chatId, String message, InlineKeyboardMarkup markupInline);

    default void execute(TelegramBot telegramBot, SendMessage sendMessage){
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // TODO Add logging to the project
        }
    };
}
