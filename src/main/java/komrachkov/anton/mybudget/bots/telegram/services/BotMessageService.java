package komrachkov.anton.mybudget.bots.telegram.services;

import komrachkov.anton.mybudget.bots.telegram.bot.TelegramBot;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface BotMessageService {

    Integer executeMessage(ExecuteMode executeMode, long chatId, Integer messageId, String message,
                           InlineKeyboardMarkup inlineKeyboardMarkup, String callbackQueryId);

    Integer sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    /**
     * @author Anton Komrachkov
     * @since 0.4
     */
    Integer sendMessageDisabledNotification(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer editMessage(long chatId, int messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer deleteMessage(long chatId, int messageId);

    void executeAndUpdateUser(TelegramUserService telegramUserService,
                              Update update, ExecuteMode executeMode, String text,
                              InlineKeyboardMarkup inlineKeyboardMarkup);

    void updateUser(TelegramUserService telegramUserService, Update update);

    /**
     * @author Anton Komrachkov
     * @since 0.4
     */
    Integer sendPopup(String callbackQueryId, String message);

    Integer execute(TelegramBot telegramBot, BotApiMethod message);
}
