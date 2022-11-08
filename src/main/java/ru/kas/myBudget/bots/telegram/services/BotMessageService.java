package ru.kas.myBudget.bots.telegram.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

public interface BotMessageService {

    void executeMessage(ExecuteMode executeMode, long chatId, Long messageId, String message,
                        InlineKeyboardMarkup inlineKeyboardMarkup);

    void executeSendMessage(long chatId, String message);

    void executeDeleteMessage(long chatId, long messageId);
}
