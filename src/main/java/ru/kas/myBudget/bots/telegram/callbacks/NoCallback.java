package ru.kas.myBudget.bots.telegram.callbacks;

import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

/**
 * @deprecated
 *
 * @since 0.2
 * @author Anton Komrachkov
 */

public class NoCallback extends CommandControllerImpl {

    public NoCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }
}
