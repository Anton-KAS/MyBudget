package ru.kas.myBudget.bots.telegram.services;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

public interface BotMessageService {

    Long executeMessage(ExecuteMode executeMode, long chatId, Long messageId, String message,
                        InlineKeyboardMarkup inlineKeyboardMarkup);

    Long executeSendMessage(long chatId, String message);

    Long executeDeleteMessage(long chatId, long messageId);

    Long executeRemoveInlineKeyboard(Update update);
    Long executeRemoveInlineKeyboard(long chatId, long messageId, String messageText);

    Long execute(TelegramBot telegramBot, BotApiMethod message);
}
