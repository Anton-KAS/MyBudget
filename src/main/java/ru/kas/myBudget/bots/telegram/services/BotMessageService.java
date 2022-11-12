package ru.kas.myBudget.bots.telegram.services;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public interface BotMessageService {

    Long executeMessage(ExecuteMode executeMode, long chatId, Long messageId, String message,
                        InlineKeyboardMarkup inlineKeyboardMarkup);

    Long sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Long editMessage(long chatId, long messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Long deleteMessage(long chatId, long messageId);

    void executeAndUpdateUser(TelegramUserService telegramUserService,
                              Update update, ExecuteMode executeMode, String text,
                              InlineKeyboardMarkup inlineKeyboardMarkup);
    void updateUser(TelegramUserService telegramUserService, Update update);

    Long execute(TelegramBot telegramBot, BotApiMethod message);
}
