package komrachkov.anton.mybudget.bots.telegram.services;

import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface BotMessageService {

    void executeAndUpdateUser(Update update, ExecuteMode executeMode, String text,
                              InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    /**
     * @author Anton Komrachkov
     * @since 0.4
     */
    Integer sendMessageDisabledNotification(Update update, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer editMessage(Update update, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer editMessage(long chatId, int messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    Integer deleteMessage(Update update);

    /**
     * @author Anton Komrachkov
     * @since 0.4
     */
    Integer sendPopup(Update update, String message);

    Integer executeMessage(ExecuteMode executeMode, Update update, String message,
                           InlineKeyboardMarkup inlineKeyboardMarkup);

    void updateUser(Update update);

    Integer execute(BotApiMethod message);
    Integer execute(BotApiMethod message, Update update);
}
