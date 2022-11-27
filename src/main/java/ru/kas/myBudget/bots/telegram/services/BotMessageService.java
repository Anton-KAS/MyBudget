package ru.kas.myBudget.bots.telegram.services;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface BotMessageService {

    Integer executeMessage(ExecuteMode executeMode, long chatId, Integer messageId, String message,
                           InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer editMessage(long chatId, int messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer deleteMessage(long chatId, int messageId);

    void executeAndUpdateUser(TelegramUserService telegramUserService,
                              Update update, ExecuteMode executeMode, String text,
                              InlineKeyboardMarkup inlineKeyboardMarkup);

    void updateUser(TelegramUserService telegramUserService, Update update);

    Integer execute(TelegramBot telegramBot, BotApiMethod message);
}
